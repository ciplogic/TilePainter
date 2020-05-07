package hellofx.common;

public class Tuple<K, V> {
    private V v2;
    private K v1;

    public Tuple() {
    }

    public Tuple(K v1, V v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public V getV2() {
        return v2;
    }

    public void setV2(V v2) {
        this.v2 = v2;
    }

    public K getV1() {
        return v1;
    }

    public void setV1(K _key) {
        this.v1 = _key;
    }

}
