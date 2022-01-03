# 몰입캠프 1주차 (12.28 ~ 01.04)
Android implementation

# 참가자
- 정민근, KAIST 19, 전산학부
- 최윤정, 숙명여대 19, 컴퓨터과학전공

# 기본적인 구성
## Tab layout & ViewPager 2
MainActivity는 3개의 tab을 가지는 Tab layout이다.  
여기에 VPAdapter(@FragmentStateAdapter)를 사용하여 ViewPager 2를 연결해주어 Tab layout과 Viewpager를 동시에 사용하였다.

## 권한 허용
앱에서 보여지는 연락처, 사진, 음악은 전부 External Storage에서 가져온다.  
즉, 휴대폰 내의 저장소에 있는 정보들을 가져오는 것이기 때문에 권한 허용이 필요하다.  
TedPermission Library를 이용하여 권한 허용 요청을 보냈으며,  
사용하는 권한은 외부 저장소 사용, 외부 연락처 사용, 카메라, 녹음 허용이다.  


#### TedPermission  
```Java
String[] permissions = new String[] {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("권한이 필요합니다.")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 활성화해야 앱이 작동합니다.")
                .setPermissions(permissions).check();
```

# Tab

## Tab 1 연락처
휴대폰 외부 저장소의 연락처 목록을 전부 받아와 오름차순으로 보여준다.  
연락처를 받아올 때 외부 저장소에 저장된 프로필 사진을 가져와 아이콘에 보여준다.  
만약 저장된 사진이 없다면 다음과 같은 사진을 보여준다.  
![person](https://user-images.githubusercontent.com/68819204/147920890-da2d34da-ee85-4e75-8b41-e7ad9a857178.png)  

연락처는 RecyclerView를 이용하여 보여주었으며 RecyclerView에 연결되는 각 아이템에는 프로필 사진과 이름, 전화번호를 보여준다. 
### 사진 추가 
여기에 FrameLayout을 사용하여 아이템 전체를 감싸는 투명 버튼을 위에 올려주어 아이템의 어떤 부분을 터치하든 상관없이 상세 정보 창으로 연결되도록 하였다.

## Tab 2 사진첩

## Tab 3 음악 플레이어
