package org.bm.checker2;

import java.util.Collection;
import java.util.Comparator;

import junit.framework.Assert;

import org.junit.Test;

public class TestChecker2 {

   @Test
   public void test() {
      CheckerManager<MultiData, ReasonCode> checkerManager = CheckerManager.<MultiData, ReasonCode> get()
         .chain(new Checker<MultiData, ReasonCode>() {

            @Override
            public ReasonCode check(MultiData object) {
               if (object.text.isEmpty()) {
                  return ReasonCode._0001.setCheckedValue(object.text);
               }
               Character c = Character.valueOf(object.text.charAt(0));
               this.addData("FirstChar", c);
               return null;
            }
         }).chain(new Checker<MultiData, ReasonCode>() {

            @Override
            public ReasonCode check(MultiData object) {
               if (object.number < 0) {
                  return ReasonCode._0002.setCheckedValue(object.number);
               }
               return null;
            }
         }).chain(new Checker<MultiData, ReasonCode>() {

            @Override
            public ReasonCode check(MultiData object) {
               if ('Y' != object.flag && 'N' != object.flag) {
                  return ReasonCode._0003.setCheckedValue(object.flag);
               }
               return null;
            }
         }).chain(new Checker<MultiData, ReasonCode>() {

            @Override
            public ReasonCode check(MultiData object) {
               Character c = (Character) this.getData("FirstChar");

               if (c == null || !c.equals(Character.valueOf('H'))) {
                  return ReasonCode._0004.setCheckedValue(c);
               }
               return null;
            }
         });

      checkerManager.setComparator(new Comparator<ReasonCode>() {

         @Override
         public int compare(ReasonCode o1, ReasonCode o2) {
            return o1.compareTo(o2);
         }
      });

      final MultiData mdOK = new MultiData();
      mdOK.text = "Hello";
      mdOK.number = 32;
      mdOK.flag = 'Y';

      final MultiData mdWrong = new MultiData();
      mdWrong.text = "";
      mdWrong.number = -2;
      mdWrong.flag = 'J';

      Collection<ReasonCode> checksOK = checkerManager.check(mdOK);
      for (ReasonCode r : checksOK) {
         System.out.println(r.getMessage());
      }
      Assert.assertEquals(0, checksOK.size());

      Collection<ReasonCode> checksKO = checkerManager.check(mdWrong);
      for (ReasonCode r : checksKO) {
         System.out.println(r.getMessage());
      }
      Assert.assertEquals(checkerManager.countChecks(), checksKO.size());

   }
}

class MultiData {
   String text;

   Integer number;

   Character flag;
};

class ReasonCode implements Comparable<ReasonCode> {
   public static final ReasonCode _0001 = new ReasonCode(1, "0001 - Text empty");

   public static final ReasonCode _0002 = new ReasonCode(2, "0002 - Negative number");

   public static final ReasonCode _0003 = new ReasonCode(3, "0003 - not Y/N flag");

   public static final ReasonCode _0004 = new ReasonCode(4, "0004 - First Letter is not H");

   private final String m;

   private final int p;

   private Object value;

   public ReasonCode(int priority, String string) {
      this.p = priority;
      m = string;

   }

   public String getMessage() {
      if (null != value) {
         return m + " >>" + value + "<<";
      }
      return m;
   }

   public int getPriority() {
      return p;
   }

   public ReasonCode setCheckedValue(Object value) {
      this.value = value;
      return this;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + p;
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      ReasonCode other = (ReasonCode) obj;
      if (p != other.p) {
         return false;
      }
      return true;
   }

   @Override
   public int compareTo(ReasonCode o) {
      if (this.equals(o)) {
         return 0;
      } else {
         if (this.p > o.p) {
            return 1;
         }
         return -1;
      }
   }

};