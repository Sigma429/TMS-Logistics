package com.sigma429.sl;

import com.sigma429.sl.domain.OrganDTO;
import com.sigma429.sl.service.OrganService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName:OrganServiceTest
 * Package:com.sigma429.sl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/30 - 22:33
 * @Version:v1.0
 */
@SpringBootTest
class OrganServiceTest {

    @Resource
    private OrganService organService;

    @Test
    void findByBid() {
        // bid值要改成自己neo4j中的值
        OrganDTO organDTO = this.organService.findByBid(1012479939628238305L);
        System.out.println(organDTO);
    }

    @Test
    void findAll() {
        // 查询包含“上海”关键字的机构
        List<OrganDTO> list = this.organService.findAll("上海");
        list.forEach(System.out::println);
    }
}