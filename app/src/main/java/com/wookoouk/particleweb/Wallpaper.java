package com.wookoouk.particleweb;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
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


//        Point userPoint;

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
        int bgColor = Color.rgb(231, 76, 60);
        int lineColor = Color.rgb(236, 240, 241);
        float maxLineDist;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            points = new ArrayList<>();
            paint.setAntiAlias(true);
            paint.setColor(lineColor);
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
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {

//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                userPoint = new Point(event.getX(), event.getY());
//            }
//            if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                if (userPoint != null) {
//                    userPoint.setPosition(event.getX(), event.getY());
//                }
//            }
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                userPoint = null;
//            }


            super.onTouchEvent(event);
        }

        private void draw() {
            SurfaceHolder holder = getSurfaceHolder();

            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(bgColor);

                    for (int i = points.size() - 1; i >= 0; i--) {
                        Point p = points.get(i);
                        if (p.x > width || p.x < 0 || p.y > height || p.y < 0) {
                            points.remove(p);
                        }
                    }

                    if (points.size() < maxNumber) {
                        addPoint();
                    }

                    updatePoints();

                    drawPoints();

                    drawLines();

                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
            handler.removeCallbacks(drawRunner);
            if (visible) {
                handler.postDelayed(drawRunner, 0);
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
            paint.setStyle(Paint.Style.FILL);
            for (Point point : points) {
                drawPoint(point);
            }
        }

        public void drawPoint(Point point) {
            canvas.drawCircle(point.x, point.y, 8f, paint);
        }

        public void drawLines() {


//            if (userPoint != null) {
//                drawPoint(userPoint);
//            }

            for (Point p : points) {
                for (Point p2 : points) {
                    if (p != p2) {
                        float distance = (float) lineDistance(p, p2);
                        if (distance < maxLineDist) {
                            drawLine(p, p2, distance);
                        }
                    }
                }
//                if (userPoint != null) {
//                    float distance = (float) lineDistance(p, userPoint);
//                    if (distance < maxLineDist) {
//                        drawLine(p, userPoint, distance);
//                    }
//                }
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
