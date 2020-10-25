public class Wrapper {
    Ray scatter;
    vector attenuation;
    vector refracted;
    public Wrapper()
    {
        scatter=new Ray();
        attenuation=new vector();
        refracted=new vector();
    }
}
