package ru.irinavb.pendulum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PendulumView extends View implements SensorEventListener {

    Paint paintCircle, paintThread;
    Path pathThread, pathHolder;

    private SensorManager sensorManager;
    private Sensor sensor;

    float angle;
    float angleX;
    float angleY;

    public PendulumView(Context context) {
        super(context);
        init(context);
    }

    public PendulumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PendulumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float originX = getWidth() / 2f;
        float originY  = 100;

        float armLength = 500;


        float bobX = (float) (armLength * Math.sin(angle)) + originX;
        float bobY = (float) (armLength * Math.cos(angle)) + originY;


        paintCircle = new Paint();
        paintThread = new Paint();

        pathThread = new Path();
        pathHolder = new Path();

        paintCircle.setStyle(Paint.Style.FILL);
        paintCircle.setColor(Color.BLACK);

        paintThread.setColor(Color.DKGRAY);
        paintThread.setStyle(Paint.Style.FILL_AND_STROKE);
        paintThread.setStrokeWidth(12);
        paintThread.setStrokeJoin(Paint.Join.ROUND);

        canvas.drawRect(originX - 300, 0, originX + 300, 100, paintThread);

        pathThread.moveTo(originX, originY);

        // ARM
        pathThread.lineTo(bobX, bobY);

        canvas.drawPath(pathThread, paintThread);
        canvas.drawPath(pathHolder, paintThread);

        // CIRCLE
        canvas.drawCircle(bobX, bobY, 100, paintCircle);

        angle += angleX;

        invalidate();
    }

    public void init(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
        angle = 0;
        angleX = 0;
        angleY = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        angle = 0;
        angleX = sensorEvent.values[0];
        angleY = sensorEvent.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
