public class vector {

    //定义最基本的数据格式：三维的浮点型坐标
    public float[] e = new float[3];

    //构造函数
    public vector() { }
    public vector(float x, float y, float z) {
        e[0] = x;
        e[1] = y;
        e[2] = z;
    }

    //获取三维坐标任一维度的索引，返回对应坐标的值
    public float x() { return e[0]; }
    public float y() { return e[1]; }
    public float z() { return e[2]; }

    //向量求和
    public vector add(vector a, vector b)
    {
        return new vector(a.e[0] + b.e[0], a.e[1] + b.e[1], a.e[2] + b.e[2]);
    }
    public vector add(vector v)
    {
        return new vector(e[0] + v.e[0], e[1] + v.e[1], e[2] + v.e[2]);
    }

    //向量相减
    public vector sub(vector a, vector b)
    {
        return new vector(a.e[0] - b.e[0], a.e[1] - b.e[1], a.e[2] - b.e[2]);
    }
    public vector sub(vector v)
    {
        return new vector(e[0] - v.e[0], e[1] - v.e[1], e[2] - v.e[2]);
    }

    //向量数乘
    public vector multiply(vector a, float c)
    {
        return new vector(a.e[0] * c, a.e[1] * c, a.e[2] * c);
    }
    public vector multiply(float t)
    {
        return new vector(e[0] * t, e[1] * t, e[2] * t);
    }

    //矩阵相乘
    public vector mult(vector a, vector b){
        return new vector(a.e[0]*b.e[0], a.e[1]*b.e[1], a.e[2]*b.e[2]);
    }
    public vector mult(vector v){
        return new vector(e[0]*v.e[0], e[1]*v.e[1], e[2]*v.e[2]);
    }

    //求向量的模长
    public float length()
    {
        return (float)Math.sqrt(
                //Math.pow((double)(e[0] + e[0]), 2.0) +
                // Math.pow((double)(e[1] + e[1]), 2.0) +
                // Math.pow((double)(e[2] + e[2]), 2.0)
                Math.pow((double)e[0], 2.0) +
                        Math.pow((double)e[1], 2.0) +
                        Math.pow((double)e[2], 2.0)
        );
    }
    //模长的平方
    public float squareLength()
    {
        return (float)(
                //Math.pow((double)(e[0] + e[0]), 2.0) +
                // Math.pow((double)(e[1] + e[1]), 2.0) +
                // Math.pow((double)(e[2] + e[2]), 2.0));
                Math.pow((double)e[0], 2.0) +
                        Math.pow((double)e[1], 2.0) +
                        Math.pow((double)e[2], 2.0)
        );
    }

    //向量归一化
    public vector normalize(vector v)
    {
        float length = v.length();
        return new vector(v.x()/length, v.y()/length, v.z()/length);
    }
    public vector normalize()
    {
        float length = this.length();
        return new vector(e[0] / length, e[1] / length, e[2] / length);
    }

    //点乘
    public float dot(vector a, vector b)
    {
        return (a.x() * b.x() + a.y() * b.y() + a.z() * b.z());
    }
    public float dot(vector v)
    {
        return (e[0]* v.e[0] + e[1] * v.e[1] + e[2] * v.e[2]);
    }

    //叉乘
    public vector cross(vector a, vector b)
    {
        return new vector(a.y() * b.z() - a.z() * b.y(),
                a.x() * b.z() - a.z() * b.x(),
                a.x() * b.y() - a.y() * b.x());
    }
    public vector cross(vector v)
    {
        return new vector(e[1] * v.z() - e[2] * v.y(),
                e[2] * v.x() - e[0] * v.z() ,
                e[0] * v.y() - e[1] * v.x());
    }
}