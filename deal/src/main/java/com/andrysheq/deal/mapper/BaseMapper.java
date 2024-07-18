package com.andrysheq.deal.mapper;

import com.andrysheq.deal.dto.CreditDTO;
import com.andrysheq.deal.entity.CreditEntity;
import org.modelmapper.ModelMapper;

import java.util.*;

public class BaseMapper extends ModelMapper {

    @Override
    public <D> D map(Object source, Class<D> destinationType) {
        if (source == null || destinationType == null) {
            return null;
        }

        return super.map(source, destinationType);
    }

    public <S, D> Set<D> convertSet(Collection<S> inSet, Class<D> destClass) {
        Set<D> result = new LinkedHashSet<>();
        if (inSet != null && destClass != null) {
            for (S e : inSet) result.add(super.map(e, destClass));
        }

        return result;
    }

    public <S, D> List<D> convertList(Collection<S> inList, Class<D> destClass) {
        List<D> result = new ArrayList<>();
        if (inList != null && destClass != null) {
            for (S e : inList) result.add(super.map(e, destClass));
        }

        return result;
    }

    public CreditDTO toDto(CreditEntity creditEntity) {
        return map(creditEntity, CreditDTO.class);
    }


}
