package escape.room.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class Disegna extends View {

    private final Paint paint;
    private final Paint textPaint;
    private final String text;
    private final Rect rect;

    public Disegna(Context context, Rect rect, String text) {
        super(context);
        this.text = text;
        this.rect = rect;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20f);
        paint.setStyle(Paint.Style.STROKE);
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(80f);
    }

    public Rect getRect() {
        return rect;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawText(text, rect.centerX()*2, rect.centerY()*2, textPaint);
        canvas.drawRect(rect.left*2, rect.top*2, rect.right*2, rect.bottom*2, paint);
    }
}