package com.sigma429.sl.service.impl;

import com.sigma429.sl.service.AddressBookService;
import com.sigma429.sl.util.PageResponse;
import com.sigma429.sl.vo.base.AddressBookVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:AddressBookServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/06 - 13:32
 * @Version:v1.0
 */
@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Override
    public AddressBookVO save(AddressBookVO vo) {
        return null;
    }

    @Override
    public AddressBookVO getById(Long id) {
        return null;
    }

    @Override
    public AddressBookVO update(AddressBookVO vo) {
        return null;
    }

    @Override
    public void saveOrderAddressWithoutBook(AddressBookVO vo) {

    }

    @Override
    public void deleteById(List<Long> ids) {

    }

    @Override
    public PageResponse<AddressBookVO> page(Integer page, Integer pageSize, String keyword, Integer type) {
        return null;
    }

    @Override
    public AddressBookVO defaultAddress() {
        return null;
    }
}
