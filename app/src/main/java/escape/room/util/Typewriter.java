package escape.room.util;

import android.os.Handler;
import android.widget.TextView;

public class Typewriter{
    private CharSequence mText;
    private int mIndex;
    private long mDelay = 5000;
    private final TextView textview;
    public Typewriter(TextView textview) {
        this.textview = textview;
    }

    private final Handler mHandler = new Handler();
    private final Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            textview.setText(mText.subSequence(0, ++mIndex));
            if (mIndex < mText.length()){
                if(mText.charAt(mIndex) == '%'){
                    mText = mText.subSequence(mIndex+1,mText.length());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mIndex = 0;
                }
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    public void animateText(CharSequence text) {
            mText = text;
            mIndex = 0;
            textview.setText("");
            mHandler.removeCallbacks(characterAdder);
            mHandler.postDelayed(characterAdder, mDelay);
    }
    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }
}