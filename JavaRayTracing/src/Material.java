
public abstract class Material {
    public abstract boolean scatter(Ray r, HitRecord rec, Wrapper wrapper);
    public vector randomInUnitSphere(){
        vector p;
        do{
            //随机坐标 区间[-1,+1]
            p =new vector((float)(Math.random()), (float)(Math.random()), (float)(Math.random())).multiply(2.0f).sub(new vector(1.0f, 1.0f, 1.0f));
        }while (p.dot(p) >= 1.0f);  //如果坐标在球内则采用，否则再次生成
        return p;
    }
}
