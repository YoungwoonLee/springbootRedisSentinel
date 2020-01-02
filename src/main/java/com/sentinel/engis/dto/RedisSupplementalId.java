package com.sentinel.engis.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;

//import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RedisHash("supId")
public class RedisSupplementalId implements Serializable {
private static final long serialVersionUID = 2586127399397901737L;
	
	@Id
	private String id;
	// ~~~.findByTpId() 되려면 Indexed애노테이션 필요...
	@Indexed 
	private String tpId;//third party id
	
	private List<Integer> categoryIds;//third party id

	@Builder
	public RedisSupplementalId(String id, String tpId, List<Integer> categoryIds) {
		super();
		this.id = id;
		this.tpId = tpId;
		this.categoryIds = categoryIds;
	}
	
	@Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString( this);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }
}
