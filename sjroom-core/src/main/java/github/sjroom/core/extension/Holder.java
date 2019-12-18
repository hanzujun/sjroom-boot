package github.sjroom.core.extension;
/**
 * @author yuzhou
 * @date 2018/3/29
 * @time 17:48
 * @since 2.0.0
 */
public class Holder<T> {
    private T object;

    public Holder(){}

    public Holder(T object) {
        this.object = object;
    }

    public T get() {
        return this.object;
    }

    public void set(T object) {
        this.object = object;
    }

    public boolean isEmpty() {
        return null == this.object;
    }
}
