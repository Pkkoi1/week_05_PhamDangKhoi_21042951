# Joppotunity - Hệ thống quản lý tuyển dụng

## Giới thiệu

Joppotunity là một trang web được thiết kế nhằm hỗ trợ việc quản lý và tổ chức thông tin liên quan đến ứng viên, công ty, và công việc một cách hiệu quả. Với giao diện thân thiện và nhiều tính năng hữu ích, hệ thống giúp cải thiện năng suất làm việc và tối ưu hóa quy trình tuyển dụng.

## Sinh viên thực hiện

- **Họ tên:** Phạm Đăng Khôi
- **Mã số sinh viên:** 21042951
- **Lớp:** DHKTPM17A
- **Giáo viên hướng dẫn:** Võ Văn Hải

## Tài liệu 
 - Word: [week_05_PhamDangKhoi_21042951_BaoCaoDeTai.docx](Document\week_05_PhamDangkhoi_21042951_BaoCaoDeTai.docx")
 - Powerpoint: 
## Công nghệ sử dụng

- **Backend:** Spring Boot, Spring Security, Spring Data JPA
- **Frontend:** Thymeleaf, HTML, CSS, JavaScript
- **Database:** MariaDB
- **Build Tool:** Gradle
- **IDE:** IntelliJ IDEA
- **Version Control:** Git, GitHub

## Yêu cầu hệ thống

### Phần cứng

- CPU: Intel Core i7
- RAM: 8GB trở lên
- Ổ cứng: SSD 256GB hoặc lớn hơn
- Màn hình: Độ phân giải Full HD (1920x1080) trở lên
- Kết nối Internet: Tốc độ ổn định

### Phần mềm

- Hệ điều hành: Windows 10/11, macOS hoặc Linux
- Java Development Kit (JDK): Phiên bản 8 hoặc cao hơn
- Database: MariaDB
- Trình duyệt: Google Chrome, Mozilla Firefox hoặc trình duyệt hiện đại khác

## Các chức năng chính

1. **Quản lý ứng viên**

   - Thêm mới và chỉnh sửa thông tin ứng viên.
   - Tìm kiếm và lọc ứng viên theo kỹ năng, kinh nghiệm.
   - Đề xuất công việc phù hợp và kỹ năng cần học.

2. **Quản lý công ty**

   - Thêm mới, chỉnh sửa thông tin công ty.
   - Xem danh sách và chi tiết thông tin các công ty.

3. **Quản lý công việc**

   - Tạo mới và cập nhật công việc.
   - Liên kết công việc với công ty liên quan.
   - Tìm kiếm và lọc công việc phù hợp với ứng viên.

4. **Tìm kiếm và lọc**
   - Tìm kiếm theo tên, vị trí, kỹ năng và nhiều tiêu chí khác.
5. **Gửi Mail ứng tuyển**
   - Gửi mail ứng tuyển cho ứng viên phù hợp

## Sơ đồ
### Class Diagram
![class_diagram.png](Figures/class_diagram.png)
### Database Diagram
![database_diagram.png](Figures/database_diagram.png)
## Giao diện website
### **Trang chủ**
   ![Home.png](Figures/Home.png)
### **Ứng viên**
**Danh sách ứng viên**

   ![listCandidate1.png](Figures/listCandidate1.png)
   ![listCandidate2.png](Figures/listCandidate2.png)

**Thêm ứng viên**
- Người dùng có thể thêm thông tin ứng viên mới bao gồm name, date of birth, phone, email, address, skills
- Nhấn nút `Add Candidate` để lưu thông tin mới.
- Nhấn nút `Back` để quay lại danh sách ứng viên.
- Sau khi thêm mới, hệ thống sẽ tự động chuyển về trang danh sách ứng viên.
   ![addCandidate2.png](Figures/addCandidate2.png)
  
**Chỉnh sửa thông tin ứng viên**
- Người dùng có thể chỉnh sửa các thông tin đã lưu của ứng viên, đảm bảo dữ liệu luôn chính xác.
- Nhấn nút `Edit` tại danh sách ứng viên hoặc trong thông tin chi tiết để lưu thông tin đã chỉnh sửa.
- Nhấn nút `Back` để quay lại danh sách ứng viên.
- Sau khi cập nhật, hệ thống sẽ tự động chuyển về trang danh sách ứng viên.
   ![upateCandidate1.png](Figures/upateCandidate1.png)
   ![updateCandidate2.png](Figures/updateCandidate2.png)
  
**Thông tin chi tiết và danh sách các kỹ năng ứng viên cần học thêm**
- Người dùng có thể xem thông tin chi tiết của ứng viên và xem các kỹ năng mà ứng viên đấy còn thiếu.
- Tại trang danh sách ứng viên, nhấn nút "Detail" của một ứng viên để xem chi tiết
   ![candidateDetail1.png](Figures/candidateDetail1.png)
   ![candidateDetail2.png](Figures/candidateDetail2.png)
  
**Công việc phù hợp với ứng viên**
- Người dùng có thể xem các công việc phù hợp với kỹ năng của ứng viên.
- Tại giao diện Công việc của Công ty, nhấn chọn "View" để xem danh sách các công việc phù hợp
   ![jobMatching.png](Figures/jobMatching.png)

### **Công ty**
**Danh sách công ty**

   ![listCompany1.png](Figures/listCompany1.png)
   ![listCompany2.png](Figures/listCompany2.png)

**Thêm công ty**
- Người dùng có thể thêm công ty mới bao gồm name, email, phone, url, infomation about company, address
- Nhấn nút `Add Company` để lưu thông tin mới.
- Nhấn nút `Back` để quay lại danh sách công ty.
- Sau khi thêm mới, hệ thống sẽ tự động chuyển về trang danh sách công ty.
   ![addCompany.png](Figures/addCompany.png)
  
**Chỉnh sửa thông tin công ty**
- Người dùng có thể chỉnh sửa các thông tin đã lưu của công ty, đảm bảo dữ liệu luôn chính xác.
- Nhấn nút `Edit` tại danh sách công ty hoặc trong thông tin chi tiết để lưu thông tin đã chỉnh sửa.
- Nhấn nút `Back` để quay lại danh sách công ty.
- Sau khi cập nhật, hệ thống sẽ tự động chuyển về trang danh sách công ty.
   ![updateCompany.png](Figures/updateCompany.png)
  
  
**Thông tin chi tiết và danh sách các công việc của công ty**
- Người dùng có thể xem thông tin chi tiết của công ty và xem các công việc mà công ty đang tuyển.
- Tại trang danh sách công ty, nhấn nút "Detail" của một công ty để xem chi tiết
   ![companyDetail.png](Figures/companyDetail.png)
  
### **Công việc*
**Danh sách công việc**

   ![ListJob.png](Figures/ListJob.png)
 
**Thêm công việc**
- Công ty có thể đăng tải công việc mới bao gồm title, description, skill required
- Nhấn nút `Add` trong giao diện thông tin chi tiết của công ty để lưu thêm công việc mới.
- Sau khi thêm mới, hệ thống sẽ tự động chuyển về giao diện thông tin chi tiết của công ty.
   ![addJob.png](Figures/addJob.png)
  
**Chỉnh sửa thông tin công việc của công ty**
- Công ty có thể chỉnh sửa thông tin của công việc cần tuyển, đảm bảo dữ liệu chính xác.
- Nhấn nút `Edit` tại công việc cần sửa ở trong giao diện thông tin chi tiết công ty để lưu thông tin đã chỉnh sửa.
- Nhấn nút `Back` để quay lại thông tin chi tiết công ty.
- Sau khi cập nhật, hệ thống sẽ tự động chuyển về trang thông tin chi tiết công ty.
   ![updateJob1.png](Figures/updateJob1.png)
   ![updateJob2.png](Figures/updateJob2.png)
  
  
**Thông tin chi tiết của công việc**
- Người dùng có thể xem thông tin chi tiết của công việc mà mình đang tìm hiểu và xem các công việc liên quan đến công ty.
- Tại trang danh sách công việc, nhấn vào tên của công việc để đi đến trang
- Nhấn nút `Back` để quay lại danh sách công việc.
  
   ![jobDetail.png](Figures/jobDetail.png)

**Danh sách ứng viện phù hợp với công việc**
- Người dùng có thể xem danh sách của các ứng viên phù hợp với công việc của công ty.
- Tại trang thông tin chi tiết của công ty, tại mục công việc chọn 'view' ở cột 'View candidate suitable'
- Nhấn nút `Back` để quay lại danh sách công việc.
  
   ![candidateMatching.png](Figures/candidateMatching.png)
   ![candidateMatching2.png](Figures/candidateMatching2.png)


### **Tìm kiếm*
**Tìm kiếm ứng viên**
- Người dùng có thể tìm kiếm ứng viên theo các tiêu chí như: kỹ năng, tên, email, số điện thoại
- Tại các giao diện: Danh sách ứng viên,  ứng viên phù hợp với công việc; trên thanh tìm kiếm, người dùng có thể nhập các tiêu chí tìm kiếm vào và nhấn nút 'find' để tìm kiếm
   ![findCandidate.png](Figures/findCandidate.png)
  
**Tìm kiếm công ty**
- Người dùng có thể tìm kiếm ứng viên theo các tiêu chí như: tên công ty, số điện thoại, link công ty, email
- Tại các giao diện: Danh sách công ty; trên thanh tìm kiếm, người dùng có thể nhập các tiêu chí tìm kiếm vào và nhấn nút 'find' để tìm kiếm
   ![findCompany.png](Figures/findCompany.png)
  
  
**Tìm kiếm công việc**
- Người dùng có thể tìm kiếm ứng viên theo các tiêu chí như: tên công việc, tên kỹ năng và công ty
- Tại các giao diện: Danh sách công việc; trên thanh tìm kiếm, người dùng có thể nhập các tiêu chí tìm kiếm vào và nhấn nút 'find' để tìm kiếm
   ![findJob.png](Figures/findJob.png)
  
### **Gửi mail ứng tuyển*
- Tại trang danh sách ứng viên phù hợp với công việc, đại diện công ty có thể gửi mail ứng tuyến đến công ty
- Nhấn nút 'Send mail' để gửi mail
   ![sendMail.png](Figures/sendMail.png)

## Kết luận và hướng phát triển

Dự án đã cơ bản đáp ứng các yêu cầu đặt ra ban đầu, hỗ trợ hiệu quả trong việc quản lý tuyển dụng. Tuy nhiên, để hệ thống hoàn thiện hơn, các hướng phát triển bao gồm:

- Bổ sung tính năng đăng nhập và phân quyền người dùng.
- Tích hợp trí tuệ nhân tạo để gợi ý công việc và hỗ trợ người dùng tốt hơn.
- Phát triển ứng dụng di động để hỗ trợ đa nền tảng.
- Cải thiện bảo mật với xác thực hai yếu tố và mã hóa dữ liệu.
