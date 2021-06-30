package com.restful.app.api.dao.extension.specifications;

import com.restful.app.api.enums.SearchOperation;
import lombok.Data;

@Data
public class SearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;
    private boolean orPredicate;

    public SearchCriteria(final String key, final SearchOperation operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

}
