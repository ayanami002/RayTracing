public class Metal extends Material{

    vector albedo;    //反射率
    float fuzz;     //镜面模糊

    public Metal() {
    }

    public Metal(vector albedo, float f) {
        this.albedo = albedo;
        if(f < 1){
            this.fuzz = f;
        }
        else {
            this.fuzz = 1;
        }
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Wrapper wrapper) {
        vector ref = reflect(r.dir(), rec.normal.normalize());
        wrapper.scatter = new Ray(rec.p, ref.add(randomInUnitSphere().multiply(fuzz)));    //p->ref
        wrapper.attenuation = albedo;
        return (ref.dot(rec.normal) > 0);
    }

    /**
     * 推导出反射光线向量
     * @param v 入射光线
     * @param n 撞击点的法向量（单位向量）
     * @return 反射光线
     */
    vector reflect(vector v, vector n)
    {
        return v.sub(n.multiply(v.dot(n)*2));
        //return v - 2 * dot(v, n)*n;
    }
}