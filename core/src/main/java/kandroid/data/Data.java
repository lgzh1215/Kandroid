package kandroid.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

import kandroid.observer.POJO;

public abstract class Data<T extends POJO> {

    protected T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
