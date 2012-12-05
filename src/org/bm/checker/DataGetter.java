package org.bm.checker;

public interface DataGetter<T> {
   /**
    * 
    * @return the object on which the checks should be applied.
    */
   T get();
}
