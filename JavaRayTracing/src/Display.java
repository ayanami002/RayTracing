import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Display {
    private int width;   //长
    private int height;  //高
    private String title;//标题

    /**
     * 设置保存路径及图片名
     *
     * @return 要保存的图片名
     */
    static String init() {
        SimpleDateFormat df = new SimpleDateFormat("HH_mm_ss");
        String outputPath = "D:\\picture\\";
        String pictureName = outputPath + "Chapter1_" + df.format(new Date()) + ".ppm";
        return pictureName;
    }




    public Display() {
        this(200, 100, "Ray Tracer");
    }
    List<Hitable> random_scene() {

        List<Hitable> objList = new ArrayList<Hitable>();
        //超大漫反射球作为地板
        objList.add(new Sphere(new vector(0.0f,-1000.0f,0.0f), 1000.0f, new Lambertian(new vector(0.5f, 0.5f, 0.5f))));
        //定义三大球
        objList.add(new Sphere(new vector(0, 1, 0), 1.0f, new glass(1.5f)));
        objList.add(new Sphere(new vector(-4, 1, 0), 1.0f, new Lambertian(new vector(0.4f, 0.2f, 0.1f))));
        objList.add(new Sphere(new vector(4, 1, 0), 1.0f, new Metal(new vector(0.7f, 0.6f, 0.5f), 0.0f)));

        //生成地面小球
        int i = 1;
        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                /*两个for循环中会产生（11+11）*(11+11)=484个随机小球*/
                float choose_mat = (float)Math.random();
                /*产生一个（0，1）的随机数，作为设置小球的材质的阀值*/
                vector center = new vector((float)( a+0.9*(Math.random()) ), 0.2f, (float) ( b+0.9*(Math.random() )));
                /*球心的x,z坐标散落在是（-11，11）之间的随机数*/
                if ((center.sub(new vector(4,0.2f,0))).length() > 0.9) {
                    /*避免小球的位置和最前面的大球的位置太靠近*/
                    if (choose_mat < 0.8) {     //diffuse
                        /*材料阀值小于0.8，则设置为漫反射球，漫反射球的衰减系数x,y,z都是（0，1）之间的随机数的平方*/
                        objList.add(
                                new Sphere(center, 0.2f, new Lambertian(
                                        new vector((float)( (Math.random())*(Math.random()) ),
                                                (float)( (Math.random())*(Math.random()) ),
                                                (float)( (Math.random())*(Math.random()) ))
                                ))
                        );
                    }
                    else if (choose_mat < 0.95) {
                        /*材料阀值大于等于0.8小于0.95，则设置为镜面反射球，镜面反射球的衰减系数x,y,z及模糊系数都是（0，1）之间的随机数加一再除以2*/
                        objList.add(
                                new Sphere(center, 0.2f, new Metal(
                                        new vector((float)( 0.5f*(1+(Math.random())) ), (float)( 0.5f*(1+(Math.random())) ), (float)( 0.5f*(1+(Math.random()))) ),
                                        (float)( 0.5*(1+(Math.random())))
                                ))
                        );
                    }
                    else {
                        /*材料阀值大于等于0.95，则设置为介质球*/
                        objList.add(
                                new Sphere(center, 0.2f, new glass(1.5f))
                        );
                    }
                }
            }
        }
        return objList;
    }
    public Display(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;



        ConsoleProgress cpb = new ConsoleProgress(0, width * height, 20, '=');
        String pictureName = Display.init();
        System.out.println("pictureName:" + pictureName);
        List<Hitable> objList = random_scene();
   /*     objList.add(new Sphere(new vector(0.0f,-1000.0f,0.0f), 1000.0f, new Lambertian(new vector(0.5f, 0.5f, 0.5f))));
        objList.add(new Sphere(new vector(0, 1, 0), 1.0f, new glass(1.5f)));
        objList.add(new Sphere(new vector(-4, 1, 0), 1.0f, new Lambertian(new vector(0.4f, 0.2f, 0.1f))));
        objList.add(new Sphere(new vector(4, 1, 0), 1.0f, new Metal(new vector(0.7f, 0.6f, 0.5f), 0.0f)));
*/
        float aspect = (float)width/(float)height;  //宽高比

        vector lookfrom = new vector(13,2,3);
        vector lookat = new vector(0,0,0);
        float dist_to_focus = (lookfrom.sub(lookat)).length();
        float aperture = 0.0f;
        Camera camera = new Camera(lookfrom, lookat, new vector(0,1,0), 20, aspect, aperture, 0.7f*dist_to_focus);
        HitableList world = new HitableList(objList);
        try {
            File f = new File(pictureName);
            if (!f.exists()) f.createNewvFile();
            FileWriter fw = new FileWriter(pictureName);
            fw.write("P3\n" + width + " " + height + "\n255\n");
            int index = 0;
            for (int j = height - 1; j >= 0; j--) {
                for (int i = 0; i < width; i++) {
                    vector col = new vector(0, 0, 0);
                    for (int k = 0; k < 100; k++) {
                        float u = (float) (i + Math.random()) / (float) width;
                        float v = (float) (j + Math.random()) / (float) height;
                        Ray r1=camera.getRay(u, v);//每一条光线
                        col = col.add(color(r1,world,0));    //根据每个像素点上色
                    }
                    index += 1;
                    col = col.multiply(1.0f / 100f);
                    col = new vector((float)Math.sqrt(col.x()), (float)Math.sqrt(col.y()), (float)Math.sqrt(col.z())); //gamma矫正
                    int ir = (int) (255.59f * col.x());
                    int ig = (int) (255.59f * col.y());
                    int ib = (int) (255.59f * col.z());
                    fw.write(ir + " " + ig + " " + ib + "\n");
                    if (index % 100 == 0) {
                        cpb.show(index);
                    }
                }
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("GG!");
        }
    }

    public vector color(Ray r,HitableList world,int depth) {
        HitRecord rec = new HitRecord();
        if (world.hit(r, 0.001f, Float.MAX_VALUE, rec)) {
            Wrapper wrapper=new Wrapper();
            if(depth<50&&rec.material.scatter(r,rec,wrapper))
            {
                return color(wrapper.scatter,world,depth+1).mult(wrapper.attenuation);
            }
            else
                {
                return new vector(0,0,0);
            }

        } else {
            //没有撞击点，绘制背景
            vector unit_dir = r.dir().normalize();  //单位方向向量
            float t = 0.5f * (unit_dir.y() + 1.0f);     //原本范围为[-1,1]调整为[0,1]
            return new vector(1.0f, 1.0f, 1.0f).multiply(1.0f - t).add(new vector(0.5f, 0.7f, 1.0f).multiply(t));
            //返回背景(1.0-t)*vec3(1.0, 1.0, 1.0) + t*vec3(0.5, 0.7, 1.0); 沿着y轴线性插值，返回的颜色介于白色与天蓝色之间
        }
    }

}