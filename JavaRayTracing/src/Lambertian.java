public class Lambertian extends Material {

    vector albedo;    //材料系数

    public Lambertian() {
    }
    public Lambertian(vector albedo) {
        this.albedo = albedo;
    }
    public boolean scatter(Ray r, HitRecord rec, Wrapper wrapper) {
        vector target = rec.p.add(rec.normal).add(randomInUnitSphere());  //相对位置->绝对位置 （p + N） + S
        wrapper.scatter = new Ray(rec.p, target.sub(rec.p));     //源点p 方向->ps
        wrapper.attenuation = albedo;
        return true;
    }

}
