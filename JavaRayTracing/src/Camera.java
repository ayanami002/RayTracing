public class Camera {
    public vector origin;
    public vector lowLeft;
    public vector high;
    public vector width;
    private float lens_radius;
    private vector u = new vector();
    private vector v = new vector();
    private vector w = new vector();
    public Camera()
    {
        origin=new vector(0.0f,0.0f,0.0f);
        lowLeft=new vector(-2.0f,-1.0f,-1.0f);
        high=new vector(0.0f,2.0f,0.0f);
        width=new vector(4.0f,0.0f,0.0f);
    }
    public Ray getRay(float u, float v)
    {
        vector rd = randomInUnitSphere().multiply(lens_radius);
        vector offset = this.u.multiply(rd.x()).add(this.v.multiply(rd.y()));
        return new Ray(origin.add(offset), lowLeft.add(width.multiply(u)).add(high.multiply(v)).sub(origin).sub(offset));
    }
    public Camera(vector lookfrom, vector lookat, vector vup, float vfov, float aspect, float aperture, float focus_dist){

        lens_radius = aperture / 2;

        float theta = (float)(vfov * Math.PI / 180);
        float half_height = (float)( Math.tan(theta/2) );
        float half_width = aspect * half_height;
        origin = lookfrom;
        w = lookfrom.sub(lookat).normalize();      //相当于新的z
        u = vup.cross(w).normalize();                   //相当于新的x
        v = w.cross(u).normalize();                     //相当于新的y
        lowLeft = origin.sub(u.multiply(half_width*focus_dist)).sub(v.multiply(half_height*focus_dist)).sub(w.multiply(focus_dist));
        width = u.multiply(2*half_width*focus_dist);
        high = v.multiply(2*half_height*focus_dist);
    }
    public vector randomInUnitSphere(){
        vector p;
        do{
            //随机坐标 区间[-1,+1]
            p =new vector((float)(Math.random()), (float)(Math.random()), 0).multiply(2.0f).sub(new vector(1.0f, 1.0f, 0.0f));
        }while (p.dot(p) >= 1.0f);  //如果坐标在球内则采用，否则再次生成
        return p;
    }

}
