package org.bm.checker;

public interface DataChecker<T> {
   /**
    * Return true if the test is successful, false if there is a problem.
    * @param object
    * @return <code>true</code> if the test is successful, <code>false</code> if there is a problem.
    */
   boolean check(T object);
}
