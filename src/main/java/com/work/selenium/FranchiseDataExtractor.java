package com.work.selenium;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

public class FranchiseDataExtractor {
    public static void main(String[] args) {
        ChromeDriver driver = new ChromeDriver();

        // 엑셀 파일 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Franchise Data");

        // 엑셀 헤더 행 생성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("본사명(사업자명)");
        headerRow.createCell(1).setCellValue("브랜드명");
        headerRow.createCell(2).setCellValue("주소1");
        headerRow.createCell(3).setCellValue("주소2");
        headerRow.createCell(4).setCellValue("우편번호");
        headerRow.createCell(5).setCellValue("가맹점 수");
        headerRow.createCell(6).setCellValue("년도");

        // 186945 ( 186944 + 1 )
        int cellCount = 0;
        for (int i = 1; i < 186945; i++) {

            try{
                driver.get("https://franchise.ftc.go.kr/mnu/00013/program/userRqst/view.do?firMstSn=" + i);
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

                WebElement webFranchiseeCount = driver.findElement(By.cssSelector("#frm > div:nth-child(13) > div > table:nth-child(4) > tbody > tr:nth-child(1) > td:nth-child(2)"));
                String franchiseeCount = webFranchiseeCount.getText();

                int cnt = Integer.parseInt(franchiseeCount);
                if(cnt < 29){
                    continue;
                }

                WebElement webHeadName = driver.findElement(By.cssSelector("#frm > div:nth-child(12) > div > table:nth-child(2) > tbody > tr:nth-child(1) > td.noborder"));
                WebElement webBrandName = driver.findElement(By.cssSelector("#frm > div:nth-child(12) > div > table:nth-child(2) > tbody > tr:nth-child(1) > td:nth-child(2)"));
                WebElement webAddress = driver.findElement(By.cssSelector("#frm > div:nth-child(12) > div > table:nth-child(3) > tbody > tr:nth-child(1) > td"));
                WebElement webYear = driver.findElement(By.cssSelector("#frm > div:nth-child(13) > div > table:nth-child(4) > thead > tr:nth-child(1) > th:nth-child(2)"));

                String headName = webHeadName.getText();
                String[] headNameSplit = headName.split(" ");
                String headNameCell = headNameSplit[1];

                String brandName = webBrandName.getText();
                String[] brandNameSplit = brandName.split(" ");
                String brandNameCell = brandNameSplit[1];

                String address = webAddress.getText();
                String[] addressSplit = address.split(" ");
                String addressCell1 = addressSplit[2]; // 우편번호
                String addressCell2 = addressSplit[3]; // 주소 1
                String addressCell3 = addressSplit[4]; // 주소 2

                String year = webYear.getText();

                // 빈값이 아닐때만 추가
                if (!headNameCell.isEmpty()
                        && !brandNameCell.isEmpty()
                        && !addressCell1.isEmpty()
                        && !addressCell2.isEmpty()
                        && !addressCell3.isEmpty()
                        && !franchiseeCount.isEmpty()
                        && !year.isEmpty()) {
                    // 데이터를 엑셀에 추가
                    cellCount++;
                    Row dataRow = sheet.createRow(cellCount);
                    System.out.println("실제 page : " + i + " & " + " cell수 : " + cellCount);

                    dataRow.createCell(0).setCellValue(headNameCell);
                    dataRow.createCell(1).setCellValue(brandNameCell);
                    dataRow.createCell(2).setCellValue(addressCell2);
                    dataRow.createCell(3).setCellValue(addressCell3);
                    dataRow.createCell(4).setCellValue(addressCell1);
                    dataRow.createCell(5).setCellValue(franchiseeCount);
                    dataRow.createCell(6).setCellValue(year);
                }

            } catch(NoSuchElementException | NumberFormatException e){

            } catch (Exception e1){
                System.out.println(i + " : " + e1.getMessage().toString());
            }
        }

        // 엑셀 파일 저장
        try (FileOutputStream outputStream = new FileOutputStream("FranchiseData.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            System.out.println("ERROR : "  + e.toString());
        }

        driver.quit();
    }
}

