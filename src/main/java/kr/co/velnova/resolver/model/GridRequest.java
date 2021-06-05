package kr.co.velnova.resolver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class GridRequest<T> {
    private int currentPage;
    private int rowCount;
    private T searchParams;

    public void setSearchParamsByObject(Object searchParams){
        this.searchParams = (T) searchParams;
    }
}
