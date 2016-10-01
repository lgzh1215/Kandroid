package kandroid.data;

import kandroid.observer.POJO;
import org.apache.commons.lang3.builder.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class POJOData<T extends POJO> {
	
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
