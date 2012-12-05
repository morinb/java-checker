package org.bm.checker;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class CheckerTest {

   @Test
   public void testCheck() {

      List<CheckError> checks = Checker.<Integer> with(new DataGetter<Integer>() {
         @Override
         public Integer get() {
            return 12;
         }
      }).addChecker(new DataChecker<Integer>() {
         @Override
         public CheckError check(Integer object) {
            if (object >= 10) {
               return new CheckError("Number is greater that 10 !");
            }

            return null;
         }
      }).check();

      Assert.assertEquals(1, checks.size()); // 1 error, because 12 >= 10 !
      System.out.println(checks.get(0).getMessage());
   }
}
