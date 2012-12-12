package org.bm.checker;

public interface DataChecker<T, E> {
   /**
    * @param object
    * @return a CheckError object, or null, if there is no problem. 
    */
   CheckError<E> check(T object);
}
