package org.bm.checker2;

import java.util.Map;

public abstract class Checker<C, R> {
   protected Checker<C, R> next;

   private Map<String, Object> dataMap;

   public void setNext(Checker<C, R> nextCheck) {
      this.next = nextCheck;
   }

   protected void setCurrentDataMap(Map<String, Object> dataMap) {
      this.dataMap = dataMap;

   }

   public abstract R check(final C object);

   public void addData(String key, Object value) {
      if (dataMap != null) {
         dataMap.put(key, value);
      }
   }

   public Object getData(String key) {
      if (dataMap != null) {
         return dataMap.get(key);
      }
      return null;
   }

   protected void doCheckAndChain(C object, Map<String, R> resultMap) {
      R result = check(object);
      if (result != null) {
         resultMap.put(getName(), result);
      }
      if (next != null) {
         next.setCurrentDataMap(dataMap);
         next.doCheckAndChain(object, resultMap);
      }
   }

   public String getName() {
      return this.getClass().getName();
   }
}
