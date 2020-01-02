package com.sentinel.engis;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.sentinel.engis.dto.RedisSupplementalId;
import com.sentinel.engis.repository.SupplementalIdRedisRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	
	@Autowired
	private SupplementalIdRedisRepository redisRepository;
	@Autowired 
	private RedisConnectionFactory factory;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 세팅된 redis마스터와 거기에 의존된 slave정보를 출력하는 테스트
	 */
	@Test
	public void getAllMasterAndSlaveTest() {
	    RedisSentinelConnection conn = stringRedisTemplate.getConnectionFactory().getSentinelConnection();

	    for (RedisServer master : conn.masters()) {
	    	System.out.println("@~~@~~~@~~@~~@~~@~~");
	        System.out.println("현재 master => " + master);// master 정보 출력
	        Collection<RedisServer> slaves = conn.slaves(master);
	        // 마스터에 의존된 모든 슬레이브 정보를 출력
	        for (RedisServer slave : slaves) {
	            System.out.println("slaves of " + master + " => " + slave);
	        }
	        System.out.println("@~~@~~~@~~@~~@~~@~~");
	    }
	}
	
	@Test
	public void countTest() {
		Long idxCnt = redisRepository.count();
		
		System.out.println("@~~@~~ idxCnt=" + idxCnt);
		
		if (idxCnt > 0) {
			redisRepository.findAll().forEach( r -> {
				System.out.println(r.getId() + ":" + r.getTpId());
				
	        	r.getCategoryIds().forEach(c -> {
	        		System.out.println("Category: " + c.intValue());
	        	});
	        });
		}
	}

	/**
	 * 레디스 전체 삭제 :: 테스트시 주의 할것 !!!!!!! @Ignore 주석처리하고 테스트 진행하면됨...
	 */
	@Test
	@Ignore
	public void redisDeleteAllTest() {
		factory.getConnection().flushDb();
	}
	
    @Test
    public void 옵스포밸류() {
    	
    	ValueOperations<String, String> vop = stringRedisTemplate.opsForValue();
    	vop.set("name", "saelobi");
//    	vop.set("frameworRk", "spring");
//    	vop.set("message", "hello world");
    	
    	String name = vop.get("name");
    	
    	System.out.println("@~~ name="+ name);
    	System.out.println("@~~ indb size="+ vop.size(name));
    }
    
	/**
	 * redis에 key값에 testkey라는 key가 존재하는지 확인.
	 */
	@Test
	public void isExistTestkey() {
		String strKey = "testkey";
		
		assertEquals(true, factory.getConnection().exists(strKey.getBytes()));
	}
	
	@Test
	public void firstOneInsert() throws Exception {
        //given
        Long incre = 1L;
        Long idxCnt = redisRepository.count();
        String tpId = "qwqwqwqwqwq";
        
        RedisSupplementalId supId = RedisSupplementalId.builder()
                .id(idxCnt+ incre +"")
                .tpId(tpId)
                .build();

        //when
        redisRepository.save(supId);
        
        RedisSupplementalId saved = redisRepository.findByTpId(tpId);
        System.out.println("@~~ saved="+ saved.toString());
	}
	
	@Test
    public void save_test() throws Exception {
//    	setUp();
    	Long id = 2L;
        String ktId = "aaaa";
        
        for (Long cnt = id; cnt < 10L; cnt++) {
        	Long idxCnt = redisRepository.count();
        	List<Integer> categoryIds = new ArrayList<>();
        	Long id1 = idxCnt+100L;
        	Long id2 = idxCnt+200L;
        	Long id3 = idxCnt+300L;
        	categoryIds.add( id1.intValue() );
        	categoryIds.add( id2.intValue() );
        	categoryIds.add( id3.intValue() );
        	
	        RedisSupplementalId rsId = RedisSupplementalId.builder()
	                .id(idxCnt+"")
	                .tpId( ktId + idxCnt )
	                .categoryIds( categoryIds )
	                .build();
	
	        RedisSupplementalId savedRsId = redisRepository.save(rsId);
        }
        
        redisRepository.findAll().forEach( r -> {
        	log.info(r.getId() + " " + r.getTpId());
        	r.getCategoryIds().forEach(c -> {
            	log.info("Category: " + c.intValue());
        	});
        });
    }
}
