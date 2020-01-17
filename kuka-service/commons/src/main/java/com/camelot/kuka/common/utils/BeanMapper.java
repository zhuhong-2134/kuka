package com.camelot.kuka.common.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 对象映射器
 * @author yingsonghao01
 * @version v1
 */
@SuppressWarnings("unused")
public class BeanMapper {

    private BeanType beanType;

    private Map<String, String> relationMap;
    
    private static class MapperFactoryHolder {
    	private static final MapperFactory INSTANCE = new DefaultMapperFactory.Builder().build();
    }
    private BeanMapper(BeanType beanType, Map<String, String> relationMap) {
        this.beanType = beanType;
        this.relationMap = relationMap;
    }

    /**
     * 默认转换
     * */
    public static BeanMapper def() {
        return new BeanMapper(BeanType.DEF, null);
    }

    /**
     * 关系型转换
     * */
    public static BeanMapper rel(Map<String, String> relationMap) {
        return new BeanMapper(BeanType.REL, relationMap);
    }

    /**
     * 简单的复制出新类型对象.
     *
     * 通过source.getClass() 获得源Class
     */
    public <S, D> D map(S source, Class<D> destinationClass) {
        return mapper(source.getClass(), destinationClass).map(source, destinationClass);
    }

    /**
     * 简单的复制出新对象列表到ArrayList
     *
     * 不建议使用mapper.mapAsList(Iterable<S>,Class<D>)接口, sourceClass需要反射，实在有点慢
     */
    public <S, D> List<D> mapList(Iterable<S> sourceList, Class<S> sourceClass, Class<D> destinationClass) {
        return mapper(sourceClass, destinationClass).mapAsList(sourceList, TypeFactory.valueOf(sourceClass), TypeFactory.valueOf(destinationClass));
    }

    /**
     * 预先获取orika转换所需要的Type，避免每次转换.
     */
    private <E> Type<E> getType(final Class<E> rawType) {
        return TypeFactory.valueOf(rawType);
    }

    private <S, T> MapperFacade mapper(Class<S> source, Class<T> target) {
        if (Objects.equals(this.beanType, BeanType.REL) && Objects.nonNull(this.relationMap)) {
            final ClassMapBuilder<S, T> classMapBuilder = MapperFactoryHolder.INSTANCE.classMap(source, target);
            this.relationMap.keySet().stream().filter(sourceField -> StringUtils.isNotBlank(sourceField) && StringUtils
                .isNotBlank(this.relationMap.get(sourceField)))
                    .forEach(sourceField -> classMapBuilder.field(sourceField, this.relationMap.get(sourceField)));
            classMapBuilder.byDefault().register();
        }
        return MapperFactoryHolder.INSTANCE.getMapperFacade();
    }

    enum BeanType {
        /** 默认 */
        DEF,
        /** 关系型 */
        REL;

        BeanType() {}
    }
}
