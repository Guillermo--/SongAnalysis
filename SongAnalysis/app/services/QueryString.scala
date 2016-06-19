package services

import java.lang.StringBuffer
import java.net.URLEncoder

class AsQueryString {
    def queryString(): String = {
      val buff: StringBuffer = new StringBuffer()
      val fields = this.getClass.getDeclaredFields
      
      for (field <- fields) {
        val value = this.getFieldValue(field.getName)
        if (value != null && !value.toString.trim().equals("-1")) {
          if (buff.length() > 0) {
            buff.append("&")
          }
          buff.append(field.getName + "=" + URLEncoder.encode(value.toString, "UTF-8"))
        }
      }

      buff.toString
    }

    private def getFieldValue(fieldName: String) = this.getClass.getDeclaredMethod(fieldName).invoke(this)
}