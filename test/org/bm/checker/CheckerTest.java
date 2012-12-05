package org.bm.checker;

import junit.framework.Assert;

import org.junit.Test;

public class CheckerTest {

   @Test
   public void testCheck() {

      boolean isLowerAndGreaterThan10 = Checker.<Integer> with(new DataGetter<Integer>() {
         @Override
         public Integer get() {
            return 12;
         }
      }).addChecker(new DataChecker<Integer>() {
         @Override
         public boolean check(Integer object) {
            return object < 10;
         }
      }).addChecker(new DataChecker<Integer>() {
         @Override
         public boolean check(Integer object) {
            return object > 10;
         }
      }).check();

      Assert.assertEquals(false, isLowerAndGreaterThan10);

   }
}
