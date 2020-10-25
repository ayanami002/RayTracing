public class Sphere extends Hitable
{
    vector center;
    float radius;
    Material mat;

    public Sphere() { }
    public Sphere(vector center, float radius, Material mat) {
        this.center = center;
        this.radius = radius;
        this.mat = mat;
    }

    /**
     * 判断与球体是否有撞击
     * @param r 光线
     * @param t_min 范围
     * @param t_max 范围
     * @param rec 撞击点
     * @return 是否有撞击
     */
    @Override
    public boolean hit(Ray r, float t_min, float t_max, HitRecord rec) {
        vector oc = r.org().sub(center);
        float a = r.dir().dot(r.dir());
        float b = 2.0f * oc.dot(r.dir());
        float c = oc.dot(oc) - radius*radius;
        float discriminant = b*b - 4.0f*a*c;
        if(discriminant > 0)
        {
            //优先选取符合范围的根较小的撞击点，若没有再选取另一个根
            float discFactor = (float)Math.sqrt(discriminant);
            float temp = (-b - discFactor) / (2.0f*a);
            if(temp < t_max && temp > t_min)
            {
                rec.t = temp;
                rec.p = r.pointAtParameter(rec.t);
                rec.normal = (rec.p.sub(center)).multiply(1.0f/radius);
                rec.material = mat;
                return true;
            }
            temp = (-b + discFactor) / (2.0f*a);
            if(temp < t_max && temp > t_min)
            {
                rec.t = temp;
                rec.p = r.pointAtParameter(rec.t);
                rec.normal = (rec.p.sub(center)).multiply(1.0f/radius);
                rec.material = mat;
                return true;
            }
        }
        return false;
    }

}