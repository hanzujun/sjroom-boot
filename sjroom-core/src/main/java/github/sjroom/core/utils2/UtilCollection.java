package github.sjroom.core.utils2;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @Auther: smj
 * @Date: 2019-04-30 14:10
 * @Description:
 */
public abstract class UtilCollection {

    public static boolean isEmpty(Collection<?> elements){
        return CollectionUtils.isEmpty(elements);
    }

    public static boolean isEmpty(Map<?, ?> map){
        return CollectionUtils.isEmpty(map);
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T> List<List<T>> split(Collection<T> values, int size) {
        if (isEmpty(values)) return new ArrayList<>(0);

        List<List<T>> result = new ArrayList<>(values.size() / size + 1);
        List<T> tmp = new ArrayList<>(size);
        for (T value : values) {
            tmp.add(value);
            if (tmp.size() >= size) {
                result.add(tmp);
                tmp = new ArrayList<>(size);
            }
        }
        if (!tmp.isEmpty()) result.add(tmp);
        return result;
    }

    public static <T> List<Set<T>> splitToSet(Collection<T> values, int size) {
        if (isEmpty(values)) return new ArrayList<>(0);

        List<Set<T>> result = new ArrayList<>(values.size() / size + 1);
        Set<T> tmp = new HashSet<>(size);
        for (T value : new LinkedHashSet<>(values)) {
            tmp.add(value);
            if (tmp.size() >= size) {
                result.add(tmp);
                tmp = new HashSet<>(size);
            }
        }
        if (!tmp.isEmpty()) result.add(tmp);
        return result;
    }

    /**
     * 返回长度
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> int size(Collection<T> collection) {
        if(collection==null) return -1;
        return collection.size();
    }

    /**
     * 返回长度
     * @param arrays
     * @param <T>
     * @return
     */
    public static <T> int size(T[] arrays) {
        if(arrays==null) return -1;
        return arrays.length;
    }

    /** 注意集合不能为空 */
    public static <T> T[] toArray(Collection<T> collection) {
        Class<?> elClass = null;
        for (T el : collection) {
            if (el != null) {
                elClass = el.getClass();
                break;
            }
        }
        if (elClass == null) throw new IllegalArgumentException("collection=" + collection);
        return toArray(collection, elClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> collection, Class<?> elClass) {
        return collection.toArray((T[]) Array.newInstance(elClass, collection.size()));
    }


    public static String[] toStringArrayDistinct(Collection<?> elements) {
        return toStrings(elements, new HashSet<>());
    }

    /** @return 集合中的元素转String */
    public static <T extends Collection<String>> String[] toStrings(Collection<?> elements, T collect) {
        for (Object element : elements) {
            collect.add(element == null ? null : element.toString());
        }
        return collect.toArray(new String[collect.size()]);
    }

}
