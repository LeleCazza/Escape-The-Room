package escape.room;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import java.util.List;
import java.util.concurrent.ExecutionException;
import escape.room.databinding.ActivityCameraBinding;

public class CameraActivity extends AppCompatActivity {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ObjectDetector objectDetector;
    private ActivityCameraBinding binding;
    private Draw element;
    private boolean trovato = false;
    private String etichetta = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LocalModel localModel = new LocalModel.Builder()
                .setAssetFilePath("model.tflite")
                .build();

        CustomObjectDetectorOptions options =
                new CustomObjectDetectorOptions.Builder(localModel)
                        .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
                        .enableClassification()
                        .setClassificationConfidenceThreshold(0.95f)
                        .setMaxPerObjectLabelCount(3)
                        .build();
        objectDetector = ObjectDetection.getClient(options);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindImageAnalysis(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder().setTargetResolution(new Size(width, height))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {
            @SuppressLint("UnsafeOptInUsageError")
            @Override
            public void analyze(@NonNull ImageProxy image) {
                int rotazione = image.getImageInfo().getRotationDegrees();
                Image immagine = image.getImage();
                if(immagine != null){
                    InputImage inputImage = InputImage.fromMediaImage(immagine, rotazione);
                    objectDetector.process(inputImage)
                            .addOnSuccessListener(
                                    new OnSuccessListener<List<DetectedObject>>() {
                                        @Override
                                        public void onSuccess(List<DetectedObject> detectedObjects) {
                                            for (DetectedObject obj : detectedObjects) {
                                                if(binding.getRoot().getChildCount() > 1){
                                                    binding.getRoot().removeViewAt(1);
                                                }
                                                if(!obj.getLabels().isEmpty()) {
                                                    if (obj.getLabels().get(0) != null) {
                                                        element = new Draw(binding.getRoot().getContext(), obj.getBoundingBox(), obj.getLabels().get(0).getText());
                                                        binding.getRoot().addView(element, 1);
                                                        trovato = true;
                                                        etichetta = obj.getLabels().get(0).getText();
                                                    }
                                                }
                                                }
                                            image.close();
                                            }
                                        }
                                    )
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            image.close();
                                        }
                                    });
                }
            }
        });

        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());
        cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector,
                imageAnalysis, preview);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(trovato){
            float x = event.getX();
            float y = event.getY();
            if(element.getRect().contains((int)x,(int)y)){
                switch (etichetta){
                    case "sapone":
                        startActivity(new Intent(this, ComputerLoginActivity.class));
                        break;
                    case "cestino":
                        startActivity(new Intent(this,CestinoActivity.class));
                        break;
                    case "cassetto":
                        startActivity(new Intent(this,CassettoActivity.class));
                        break;
                    case "lavagna":
                        startActivity(new Intent(this,LavagnaActivity.class));
                        break;
                    case "banco":
                        startActivity(new Intent(this,BancoActivity.class));
                        break;
                    default:
                        break;
                }
            }
            trovato = false;
        }
        return super.onTouchEvent(event);
    }
}