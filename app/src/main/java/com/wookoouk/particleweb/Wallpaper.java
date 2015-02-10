package com.wookoouk.particleweb;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wallpaper extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new ParticleWebEngine();
    }

    public class ParticleWebEngine extends Engine {
        private final Handler handler = new Handler();

        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                draw();
            }

        };
        private List<Point> points;
        private Paint paint = new Paint();
        private int width;
        int height;
        private boolean visible = true;
        private int maxNumber = 50;
        Canvas canvas = null;
        String defaultBG = "#E74C3C";
        String defaultPoint = "#ECF0F1";
        Boolean showPoints;
        int bgColor;
        int lineColor;
        float maxLineDist;

        public void updatePreferences() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            bgColor = Color.parseColor(preferences.getString(SettingsActivity.bgColorKey, defaultBG));
            lineColor = Color.parseColor(preferences.getString(SettingsActivity.pointColorKey, defaultPoint));
            showPoints = preferences.getBoolean(SettingsActivity.showPoints, true);

            paint.setColor(lineColor);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            updatePreferences();

            points = new ArrayList<>();
            paint.setAntiAlias(true);


            handler.post(drawRunner);


        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawRunner);
            } else {
                handler.removeCallbacks(drawRunner);
            }
            updatePreferences();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            handler.removeCallbacks(drawRunner);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {
            this.width = width;
            this.height = height;
            maxLineDist = width / 5;
            maxNumber = (width / 50) + (height / 50);
            super.onSurfaceChanged(holder, format, width, height);
        }

        private void draw() {
            SurfaceHolder holder = getSurfaceHolder();

            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(bgColor);

                    for (int i = points.size() - 1; i >= 0; i--) {
                        Point p = points.get(i);
                        if (p.x > width + maxLineDist || p.x < -maxLineDist || p.y > height + maxLineDist || p.y < -maxLineDist) {
                            points.remove(p);
                        }
                    }

                    if (points.size() < maxNumber) {
                        addPoint();
                    }

                    updatePoints();

                    if (showPoints) {
                        drawPoints();
                    }

                    drawLines();

                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
            handler.removeCallbacks(drawRunner);
            if (visible) {
                handler.post(drawRunner);
            }
        }

        public void addPoint() {
            Random random = new Random();
            float x = random.nextFloat() * (width);
            float y = random.nextFloat() * (height);
            points.add(new Point(x, y));
        }

        public void updatePoints() {
            for (Point point : points) {
                point.translate();
            }
        }

        public void drawPoints() {
//            Log.e("APP", paint.getColor()+"");
            paint.setStyle(Paint.Style.FILL);
            for (Point point : points) {
                drawPoint(point);
            }
        }

        public void drawPoint(Point point) {
            canvas.drawCircle(point.x, point.y, 6f, paint);
        }

        public void drawLines() {

            for (Point p : points) {
                for (Point p2 : points) {
                    if (p != p2) {
                        float distance = (float) lineDistance(p, p2);
                        if (distance < maxLineDist) {
                            drawLine(p, p2, distance);
                        }
                    }
                }
            }
        }

        public void drawLine(Point p, Point p2, float distance) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            float invertedDistance = ((maxLineDist - 0) - distance) / 100;
            paint.setStrokeWidth(invertedDistance);

            canvas.drawLine(p.x, p.y, p2.x, p2.y, paint);
        }

        public double lineDistance(Point p, Point p2) {
            float xs = p2.x - p.x;
            xs = xs * xs;

            float ys = p2.y - p.y;
            ys = ys * ys;

            return Math.sqrt(xs + ys);
        }
    }
}
