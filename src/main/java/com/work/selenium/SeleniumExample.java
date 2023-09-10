package com.work.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumExample {
    public static void main(String[] args) throws InterruptedException {

        // Selenium WebDriver 설정
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");

        // Chrome 브라우저 옵션 설정
        ChromeOptions options = new ChromeOptions();

        ChromeDriver driver = new ChromeDriver(options);

        /**
         * 검색어 : 놀부
         * 본사명(사업자명) -> 상호
         * 브랜드명 (복수) -> 영업표지
         * 지역(본사의 도/시까지, 서울/구) -> 주소 ///   우편번호 : 06072 , 시 : 서울특별시  // 구 :  강남구
         * 가맹점수 (없거나, 제로 제외, 30개 이상) ->  가맹점 수
         */

        for (int i = 1; i < 5; i++){

            driver.get("https://franchise.ftc.go.kr/mnu/00013/program/userRqst/view.do?firMstSn="+i);

            Thread.sleep(1000);
            WebElement element1 = driver.findElement(By.cssSelector("#frm > div:nth-child(12) > div > table:nth-child(2) > tbody > tr:nth-child(1) > td.noborder > label"));

            Thread.sleep(1000);
            WebElement element2 = driver.findElement(By.cssSelector("#frm > div:nth-child(12) > div > table:nth-child(3) > tbody > tr:nth-child(1) > td"));

            Thread.sleep(1000);
            WebElement element3 = driver.findElement(By.cssSelector("#frm > div:nth-child(13) > div > table:nth-child(8) > tbody > tr:nth-child(1) > td:nth-child(2)"));



            System.out.println("element1 = " + element1);
            System.out.println("element2 = " + element2);
            System.out.println("element3 = " + element3);

            /**
             *
             * #frm > div:nth-child(12) > div > table:nth-child(2) > tbody > tr:nth-child(1) > td.noborder > label
             *
             * <div id="frm>
             *      ...
             *      <div>
             *          <div>
             *              <table></table>
             *              <table>
             *                  <tbody>
             *                      <tr>
             *                          <td class='noborder'>
             *                              <label></label>
             *                          </td>
             *                      </tr>
             *                  </tbody>
             *              </table>
             *          </div>
             *      </div> // 12번째
             * </div>
             */
            driver.quit();

        }

    }
}

