package v.yeikovych.tinprojectsp.dto;


public interface Dto<E> {

    E toEntity();

    Dto<E> copyFrom(E other);
}
