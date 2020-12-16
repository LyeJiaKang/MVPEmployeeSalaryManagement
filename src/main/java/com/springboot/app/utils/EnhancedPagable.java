package com.springboot.app.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class EnhancedPagable extends PageRequest{

	private static final long serialVersionUID = 1L;
	private int offset;
	    public EnhancedPagable(int offset, int limit,Sort sort){
	        super(offset,limit, sort);
	        this.offset=offset;
	    }
	    
	    @Override
	    public long getOffset(){
	        return this.offset;
	    }

}
