public class HitRecord {
    public float t;//碰撞参数
    public vector p;//碰撞坐标
    public vector normal;//碰撞法向量
    public Material material;//碰撞材料
    public HitRecord()
    {
        t=0;
        p=new vector(0,0,0);
        normal=new vector(0,0,0);
    }
}
