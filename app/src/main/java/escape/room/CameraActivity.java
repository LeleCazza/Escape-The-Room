package escape.room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import java.util.concurrent.ExecutionException;
import escape.room.databinding.ActivityCameraBinding;
import escape.room.util.Abilita;
import escape.room.util.Disegna;

public class CameraActivity extends AppCompatActivity {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ObjectDetector objectDetector;
    private ActivityCameraBinding binding;
    private Disegna elemento;
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
        CustomObjectDetectorOptions options = new CustomObjectDetectorOptions.Builder(localModel)
            .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
            .enableClassification()
            .setClassificationConfidenceThreshold(0.7f)
            .setMaxPerObjectLabelCount(3)
            .build();
        objectDetector = ObjectDetection.getClient(options);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindImageAnalysis(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
        startCountDown();
    }

    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(width, height))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), image -> {
            int rotazione = image.getImageInfo().getRotationDegrees();
            @SuppressLint("UnsafeOptInUsageError") Image immagine = image.getImage();
            if(immagine != null){
                InputImage inputImage = InputImage.fromMediaImage(immagine, rotazione);
                objectDetector.process(inputImage)
                    .addOnSuccessListener( detectedObjects -> {
                        for (DetectedObject obj : detectedObjects) {
                            if(binding.getRoot().getChildCount() > 1)
                                binding.getRoot().removeViewAt(1);
                            if(!obj.getLabels().isEmpty())
                                if (obj.getLabels().get(0) != null) {
                                    elemento = new Disegna(
                                            binding.getRoot().getContext(),
                                            obj.getBoundingBox(),
                                            obj.getLabels().get(0).getText()
                                    );
                                    binding.getRoot().addView(elemento, 1);
                                    etichetta = obj.getLabels().get(0).getText();
                                    trovato = true;
                                }
                        }
                        image.close();
                    })
                    .addOnFailureListener(e -> image.close());
            }
        });
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());
        cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(trovato){
            float x = event.getX();
            float y = event.getY();
            Rect rect = new Rect(elemento.getRect().left*2,elemento.getRect().top*2,
                    elemento.getRect().right*2,elemento.getRect().bottom*2);
            if(rect.contains((int)x,(int)y)){
                switch (etichetta){
                    case "computer":
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

    @Override
    protected void onStart() {
        super.onStart();
        controlloAbilitaOttenute();
    }

    private void startCountDown(){
        String attivaCountdown = "si";
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            attivaCountdown = extras.getString("countdown");
        if(attivaCountdown.equals("si")){
            TextView countdownTextView = findViewById(R.id.countdown_textview_camera);
            countdownTextView.setTextColor(Color.RED);
            new CountDownTimer(ImpostazioniActivity.tempo, 1000) {
                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long millisUntilFinished) {
                    long minutes = millisUntilFinished / 60000;
                    long seconds = (millisUntilFinished % 60000) / 1000;
                    countdownTextView.setText(String.format("%02d:%02d", minutes, seconds));
                }
                @Override
                public void onFinish() {
                    countdownTextView.setText("");
                    startActivity(new Intent(CameraActivity.this,PausaActivity.class));
                    finish();
                }
            }.start();
        }
        else
            findViewById(R.id.countdown_textview_camera).setVisibility(View.INVISIBLE);
    }

    private void controlloAbilitaOttenute() {
        if(Abilita.CERCATORE_DI_INFORMAZIONI_AVANZATO_1 && Abilita.CERCATORE_DI_INFORMAZIONI_AVANZATO_2){
            Toast.makeText(this,
                    "HAI OTTENUTO L'ABILITÀ CERCATORE DI INFORMAZIONI AVANZATO!",
                    Toast.LENGTH_LONG).show();
            Abilita.CERCATORE_DI_INFORMAZIONI_AVANZATO = true;
            Abilita.CERCATORE_DI_INFORMAZIONI_AVANZATO_1 = false;
            Abilita.CERCATORE_DI_INFORMAZIONI_AVANZATO_2 = false;
        }
    }

    @Override
    public void onBackPressed() {
        //tasto indietro disabilitato
    }
}