
import static java.lang.Math.*;
import java.util.Vector;

public class Geometry {

    static class Point {

        public double x, y;

        public Point() {}

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + " , " + y + ")";
        }

        public Point add(Point p) {
            return new Point(x + p.x, y + p.y);
        }

        public void add(double v) {
            x += v;
            y += v;
        }

        public void sub(double v) {
            x -= v;
            y -= v;
        }

        public void mul(double v) {
            x *= v;
            y *= v;
        }

        public void div(double v) {
            x /= v;
            y /= v;
        }

        public Point sub(Point p) {
            return new Point(x - p.x, y - p.y);
        }

        public Point mul(Point p) {
            return new Point(x * p.x, y * p.y);
        }

        public Point div(Point p) {
            return new Point(x / p.x, y / p.y);
        }

        public double distance(Point other) {
            return Math.hypot(x - other.x, y - other.y);
        }

        // cross product of two vectors
        public double cross(Point p) {
            return x * p.y - y * p.x;
        }

        //rotate vector by angle theta
        public Point rotate(double theta) {
            double rad = toRadians(theta);
            double x0 = x * cos(rad) - y * sin(rad);
            double y0 = x * sin(rad) + y * cos(rad);
            return new Point(x0, y0);
        }

        //dot product AB.BC
        public double dot(Point b, Point c) {
            double ABx = b.x - x, ABy = b.y - y, BCx = c.x - b.x, BCy = c.y - b.y;
            return ABx * BCx + ABy * BCy;
        }

        //cross product ABxBC
        public double cross(Point b, Point c) {
            double ABx = b.x - x, ABy = b.y - y, ACx = c.x - x, ACy = c.y - y;
            return ABx * ACy - ABy * ACx;
        }

        //given 2 points return line coff a,b,c
        public Line pointsToLine(Point a, Point b) {
            if (a.x == b.x) {
                return new Line(1, 0, -a.x);
            } else {
                Line l = new Line();
                l.a = -((a.y - b.y) / (a.x - b.x));
                l.b = 1;
                l.c = -((l.a * a.x) - (l.b * a.y));
                return l;
            }
        }

       // is a->b->c a counter-clockwise turn?
        // -1 if clockwise, +1 if counter-clockwise, 0 if collinear
        public int ccw(Point b, Point c) {
            double area2 = (b.x - x) * (c.y - y) - (b.y - y) * (c.x - x);
            if (area2 < 0) {
                return -1;
            } else if (area2 > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    static class Line {

        public double a, b, c;

        public Line() {
        }

        public Line(Point a, Point b) {
            this.a = a.y - b.y;
            this.b = b.x - a.x;
            this.c = -a.x * this.a - a.y * this.b;
        }

        public Line(double a, double b, double c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public boolean areParallel(Line l) {
            return abs(a - l.a) < 1e-6 && abs(b - l.b) < 1e-6;
        }
        
        public boolean areParallel(Point a,Point b,Point c,Point d) {
            double slop1 = (b.y-a.y)/(b.x-a.x);
            double slop2 = (d.y-c.y)/(d.x-c.x);
            
            return abs(slop1-slop2) < 1e-6;
        }

        public boolean areSame(Line l) {
            return areParallel(l) && abs(c - l.c) < 1e-6;
        }

        //compute distance from ab to 
        public double linePointDist(Point a, Point b, Point c) {
            double dist = a.cross(b, c) / a.distance(b);
            return abs(dist);
        }

        public Point intesect(Line second) {
            if (Math.abs(a * second.b - b * second.a) == 0) {
                return null;
            }
            double x = (b * second.c - c * second.b) / (a * second.b - b * second.a);
            double y = (a * second.c - c * second.a) / (b * second.a - a * second.b);

            return new Point(x, y);
        }
    }

    static class Segment {

        public Point a, b;

        public Segment(Point a, Point b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return "{" + a + " " + b + "}";
        }

        public double length() {
            return a.distance(b);
        }

        public Point middle() {
            return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
        }

        public boolean inside(Point p) {
            return Math.abs(length() - p.distance(a) - p.distance(b)) < 1e-6;
        }
        
        public boolean areParallel(Segment s) {
            double slop1 = (b.y-a.y)/(b.x-a.x);
            double slop2 = (s.b.y-s.a.y)/(s.b.x-s.a.x);

            return abs(slop1-slop2) < 1e-6;
        }

        public Point intesect(Segment other) {
            Line first = new Line(a, b);
            Line second = new Line(other.a, other.b);
            Point p = first.intesect(second);

            if (p != null && inside(p) && other.inside(p)) {
                return p;
            }
            return null;
        }

        public double linePointDist(Point a, Point b, Point c) {
            double dist = a.cross(b, c) / a.distance(b);
            double dot1 = a.dot(b, c);
            if (dot1 > 0) {
                return b.distance(c);
            }
            double dot2 = b.dot(a, c);
            if (dot2 > 0) {
                return a.distance(c);
            }
            return abs(dist);
        }

        public boolean onSegment(Point pi, Point pj, Point pk) {
            if (min(pi.x, pj.x) <= pk.x && max(pi.x, pj.x) >= pk.x) {
                if (min(pi.y, pj.y) <= pk.y && max(pi.y, pj.y) >= pk.y) {
                    return true;
                }
            }
            return false;
        }

        //line intersection
        public boolean SegmentsIntersect(Point p1, Point p2, Point p3, Point p4) {
            double d1 = p3.cross(p4, p1);
            double d2 = p3.cross(p4, p2);
            double d3 = p1.cross(p2, p3);
            double d4 = p1.cross(p2, p4);

            if ((d1 > 0 && d2 < 0 || d1 < 0 && d2 > 0) && (d3 > 0 && d4 < 0 || d3 < 0 && d4 > 0)) {
                return true;
            } else if (d1 == 0 && onSegment(p3, p4, p1)) {
                return true;
            } else if (d2 == 0 && onSegment(p3, p4, p2)) {
                return true;
            } else if (d3 == 0 && onSegment(p1, p2, p3)) {
                return true;
            } else if (d4 == 0 && onSegment(p1, p2, p4)) {
                return true;
            } else {
                return false;
            }
        }

        // Segment intersect line AB
        public Point intersectLine(Line AB) {
            double a1 = AB.a, b1 = AB.b, c1 = AB.c;
            double u = abs(a1 * a.x + b1 * a.y + c1);
            double v = abs(a1 * b.x + b1 * b.y + c1);

            return new Point((a.x * v + a.x * u) / (u + v), (a.y * v + b.y * u) / (u + v));
        }
    }

    static class Polygon {

        public double angle(Point a, Point b, Point c) {
            double ux = b.x - a.x, uy = b.y - a.y;
            double vx = c.x - a.x, vy = c.y - a.y;
            double x = ux * vx + uy * vy;
            double y = Math.sqrt((ux * ux + uy * uy) * (vx * vx + vy * vy));
            return Math.acos(x / y);
        }

        public boolean inPolygon(Point p, Point[] points) {
            double sum = 0.0;
            for (int i = 0; i < points.length - 1; i++) {
                if (p.cross(points[i], points[i + 1]) < 0) {
                    sum -= angle(p, points[i], points[i + 1]);
                } else {
                    sum += angle(p, points[i], points[i + 1]);
                }
            }

            return abs(sum - 2 * Math.PI) < 1e-6 || (sum + 2 * Math.PI) < 1e-6;
        }

        public double perimeter(Point[] p) {
            double res = 0;
            for (int i = 0; i < p.length - 1; i++) {
                res += p[i].distance(p[i + 1]);
            }
            return res;
        }

        public double polArea(Point[] p) {
            double area = 0;
            int N = p.length;

            for (int i = 1; i + 1 < N; i++) {
                double x1 = p[i].x - p[0].x;
                double y1 = p[i].y - p[0].y;
                double x2 = p[i + 1].x - p[0].x;
                double y2 = p[i + 1].y - p[0].y;
                double cross = x1 * y2 - x2 * y1;
                area += cross;
            }

            return abs(area / 2.0);
        }

        public boolean isConvex(Point[] p) {
            boolean dir = p[0].ccw(p[1], p[2]) > 0;
            boolean flag;
            for (int i = 1; i < p.length; i++) {
                flag = p[i].ccw(p[(i + 1) % p.length], p[(i + 2) % p.length]) > 0;
                if (dir != flag) {
                    return false;
                }
            }
            return true;
        }

        public Vector<Point> cutPolygon(Point a, Point b, Vector<Point> q) {
            q.add(q.get(0));
            Vector<Point> p = new Vector<Point>();

            for (int i = 0; i < q.size(); i++) {
                double left1 = a.cross(b, q.get(i)), left2 = 0;
                if (i != q.size() - 1) {
                    left2 = a.cross(b, q.get(i + 1));
                }
                if (left1 > -1e-6) {
                    p.add(q.get(i));
                }

                if (left1 * left2 < -1e-6) {
                    Segment s = new Segment(q.get(i), q.get(i + 1));
                    Point ans = s.intersectLine(new Line(a, b));
                    p.add(new Point(ans.x, ans.y));
                }
            }
            return p;
        }
    }

    static class Triangle {

        Point[] vertices;
        Segment[] sides;

        public Triangle(Point p1, Point p2, Point p3) {
            vertices = new Point[]{p1, p2, p3};
            sides = new Segment[]{new Segment(vertices[0], vertices[1]), new Segment(vertices[1], vertices[2]), new Segment(vertices[2], vertices[0])};
        }

        public double area(double b, double h) {
            return 0.5 * b * h;
        }

        public double area(double a, double b, double c) {
            double s = semiPerimeter(a, b, c);
            return sqrt(s * (s - a) * (s - b) * (s - c));
        }

        public double perimeter(double a, double b, double c) {
            return a + b + c;
        }

        public double semiPerimeter(double a, double b, double c) {
            return perimeter(a, b, c) / 2.0;
        }

        public double inCircle(double area, double semiP) {
            return area / semiP;
        }

        public double circumCircle(double a, double b, double c) {
            double A = area(a, b, c);
            return a * b * c / (4 * A);
        }

        public boolean canFormT(double a, double b, double c) {
            return a + b > c && a + c > b && b + c > a;
        }

        public double cosineRule(double a, double b, double theta) {
            return sqrt(a * b + b * b - 2 * a * b * cos(theta));
        }

        public double getTheta(double a, double b, double c) {
            return acos((a * a + b * b - c * c) / 2 * a * b);
        }

        public Line perBisector(Point a, Point b) {
            Point mid = b.add(a);
            mid.mul(0.5);
            double x = b.x - a.x;
            double y = b.y - a.y;
            Point p1 = new Point(-1 * y, x);
            Point p2 = new Point(y, -1 * x);
            p1 = p1.add(mid);
            p2 = p2.add(mid);
            return new Line(p1, p2);
        }

        public double square(Point a, Point b, Point c) {
            return 0.5 * abs((a.x - b.x) * (a.y + b.y) + (b.x - c.x) * (b.y + c.y) + (c.x - a.x) * (c.y + a.y));
        }

        public double square() {
            return square(vertices[0], vertices[1], vertices[2]);
        }

        public boolean inside(Point p) {
            double square = square();
            double allegendSquare = 0;
            for (int i = 0; i < 3; i++) {
                allegendSquare += square(p, vertices[(i + 1) % 3], vertices[(i + 2) % 3]);
            }
            return abs(square - allegendSquare) < 1e-6;
        }
    }

    static class Circle {

        public Point center;
        public double radius;

        public Circle(Point c, double r) {
            this.center = c;
            this.radius = r;
        }

        public boolean contains(Point p) {
            return p.distance(center) <= radius;
        }

        public double area() {
            return PI * radius * radius;
        }

        public double area(double r) {
            return PI * r * r;
        }

        public double perimeter() {
            return 2 * PI * radius;
        }

        public boolean intersects(Circle c) {
            return center.distance(c.center) <= radius + c.radius;
        }

        public double circumFerence(double r) {
            return 2 * r * PI;
        }

        public double arcLength(double r, double theta) {
            return (theta / 360) * circumFerence(r);
        }

        public double chrodLength(double r, double theta) {
            return sqrt(2 * r * r * (1 - cos(theta)));
        }

        public double sectorArea(double r, double theta) {
            return r * r * theta / 2;
        }

        public double triangleArea(double r, double theta) {
            return r * r * sin(theta) / 2;
        }

        // sector area - the area of triangle r,r,chrodlength
        public double segmentArea(double r, double theta) {
            return sectorArea(r, theta) - triangleArea(r, theta);
        }

        // does circle (x1, y1, r1) intersect circle (x2, y2, r2)?
        public boolean intersects(double x1, double y1, double r1, double x2, double y2, double r2) {
            double dx = x1 - x2;
            double dy = y1 - y2;
            double distance = sqrt(dx * dx + dy * dy);
            return distance <= r1 + r2;
        }

        public double intersectionArea(double x1, double y1, double r1, double x2, double y2, double r2) {
            if (r2 < r1) {
                double temp = r2;
                r2 = r1;
                r1 = temp;
            }

            Point p1 = new Point(x1, y1);
            Point p2 = new Point(x2, y2);
            double d = p1.distance(p2);

            double part1 = r1 * r1 * acos((d * d + r1 * r1 - r2 * r2) / (2 * d * r1));
            double part2 = r2 * r2 * acos((d * d + r2 * r2 - r1 * r1) / (2 * d * r2));
            double part3 = 0.5 * sqrt((-d + r1 + r2) * (d + r1 - r2) * (d - r1 + r2) * (d + r1 + r2));

            return part1 + part2 - part3;
        }
    }

    static class Sphere {

        double gcDistance;

        public double getGcDistance() {
            return gcDistance;
        }

        public Sphere(double pLat, double pLong, double qLat, double qLong,   double r) {
            pLat = rad(pLat);
            qLat = rad(qLat);
            pLong = rad(pLong);
            qLong = rad(qLong);
            double a = cos(pLat) * cos(pLong) * cos(qLat) * cos(qLong);
            double b = cos(pLat) * sin(pLong) * cos(qLat) * sin(qLong);
            double c = sin(pLat) * sin(qLat);
            gcDistance = r * acos(a + b + c);
        }

        double acos(double theta) {
            return Math.acos(theta);
        }

        double rad(double theta) {
            return Math.toRadians(theta);
        }

        double cos(double a) {
            return Math.cos(a);
        }

        double sin(double a) {
            return Math.sin(a);
        }

    }
}

