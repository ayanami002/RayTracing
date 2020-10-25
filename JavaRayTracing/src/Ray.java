public class Ray {
    public vector o;
    public vector d;
    public Ray(){}
    public Ray(vector origin,vector direction)
    {
        this.o=origin;
        this.d=direction;
    }
    public vector dir()
    {
        return d;
    }
    public vector org()
    {
        return o;
    }
    public vector pointAtParameter(float t)
    {
        return o.add(d.multiply(t));
    }
}
