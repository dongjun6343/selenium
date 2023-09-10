package com.work.selenium.service;

import com.work.selenium.domain.Franchise;
import com.work.selenium.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.time.Duration;


/**
 * 트랜잭션 고민.
 * 100개 후 commit
 */
@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepository franchiseRepository;

    public int franchiseDataExtractor(int start, int end) {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless"); // 브라우저 창을 표시하지 않음
        ChromeDriver driver = new ChromeDriver(options);
        int pageNum = 0;
        for (int i = start; i <= end; i++) {
            pageNum = i;
            try{
                driver.get("https://franchise.ftc.go.kr/mnu/00013/program/userRqst/view.do?firMstSn=" + i);
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

                WebElement webHeadName = driver.findElement(By.cssSelector("#frm > div:nth-child(12) > div > table:nth-child(2) > tbody > tr:nth-child(1) > td.noborder"));
                String headName = webHeadName.getText();
                if(headName.isEmpty()){
                    continue;
                }

                WebElement webFranchiseeCount = driver.findElement(By.cssSelector("#frm > div:nth-child(13) > div > table:nth-child(4) > tbody > tr:nth-child(1) > td:nth-child(2)"));
                String franchiseCount = webFranchiseeCount.getText();

                int cnt = Integer.parseInt(franchiseCount);
                if(cnt < 29){
                    continue;
                }

                WebElement webBrandName = driver.findElement(By.cssSelector("#frm > div:nth-child(12) > div > table:nth-child(2) > tbody > tr:nth-child(1) > td:nth-child(2)"));
                WebElement webAddress = driver.findElement(By.cssSelector("#frm > div:nth-child(12) > div > table:nth-child(3) > tbody > tr:nth-child(1) > td"));
                WebElement webYear = driver.findElement(By.cssSelector("#frm > div:nth-child(13) > div > table:nth-child(4) > thead > tr:nth-child(1) > th:nth-child(2)"));

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

                String franchiseYear = webYear.getText();

                createFranchiseData(headNameCell, brandNameCell, addressCell1, addressCell2 ,addressCell3 , franchiseYear, cnt, pageNum);
            } catch(NoSuchElementException | NumberFormatException e){

            } catch (Exception e1){
                System.out.println(i + " : " + e1.getMessage().toString());
            }
        }
        driver.quit();

        return pageNum;
    }

    public void createFranchiseData(String headNameCell, String brandNameCell, String addressCell1, String addressCell2, String addressCell3, String franchiseYear, int cnt, int pageNum){

        Franchise franchise = Franchise.builder()
                        .headName(headNameCell)
                        .brandName(brandNameCell)
                        .address1(addressCell1)
                        .address2(addressCell2)
                        .address3(addressCell3)
                        .franchiseYear(franchiseYear)
                        .franchiseCount(cnt)
                        .pageNum(pageNum)
                        .build();

        franchiseRepository.save(franchise);
    }

    public void excelCreate(){
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
    }
}

