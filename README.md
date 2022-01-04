# 브링Bring  


## 팀원  
- 정민근, KAIST 19, 전산학부
- 최윤정, 숙명여대 19, 컴퓨터과학전공  

# 기본적인 구성
<img src="https://user-images.githubusercontent.com/49242646/148054090-300991dc-dd5f-446f-9287-9678d88f869a.gif" width="205" height="411"/>
+ ### Splash Screen  
앱을 실행하면 앱 이름(브링Bring)을 보여주는 Splash Screen이 2초 동안 뜨고 MainActivity로 넘어간다.

+ ### **Tab layout & ViewPager 2**  
MainActivity는 3개의 tab을 가지는 Tab layout이다.  
여기에 VPAdapter(@FragmentStateAdapter)를 사용하여 ViewPager 2를 연결해주어 Tab layout과 Viewpager를 동시에 사용하였다.

+ ### 권한 허용  
앱에서 보여지는 연락처, 사진, 음악은 전부 External Storage에서 가져온다.  
즉, 휴대폰 내의 저장소에 있는 정보들을 가져오는 것이기 때문에 권한 허용이 필요하다.  
TedPermission Library를 이용하여 권한 허용 요청을 보냈으며,  
사용하는 권한은 외부 저장소 사용, 외부 연락처 사용, 카메라, 녹음 허용이다.  

### TedPermission  
```Java
String[] permissions = new String[] {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("권한이 필요합니다.")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 활성화해야 앱이 작동합니다.")
                .setPermissions(permissions).check();
```

+ ### 다크 모드  
휴대폰 내의 다크 모드를 누르면, 앱에 다크 모드가 적용되어 보여진다.  


# Tab
<img src="https://user-images.githubusercontent.com/49242646/148053927-3bb09ee2-24ff-4142-bb52-c15f71270bde.gif" width="205" height="411"/>
+ ### Tab 1 연락처  
  + MainActivity  
MainActivity는 외부 저장소의 연락처를 RecyclerView를 이용하여 전부 보여준다.  
각 아이템을 클릭하면 ContactActivity2를 호출하여 연락처가 가지고 있는 사진, 이름, 전화번호를 크게 보여준다.  
SwipeRefreshView가 적용되어 있어 연락처의 추가, 수정, 삭제 등 변화가 생기면 swipe refresh를 통해 바로 확인할 수 있다.  
'+' 버튼은 클릭하면 새로운 연락처를 추가하는 AddContact Activity를 호출한다.   


  + ContactActivity2  
ContactActivity2는 연락처의 정보를 크게 보여주며 상단 메뉴에 삭제, 수정 메뉴를 포함하고 있다.  
삭제를 클릭하면 해당 연락처를 외부 저장소에서 삭제하며  
수정을 클릭할 경우 해당 연락처를 수정하여 외부 저장소에 변화를 저장하는 ChangeContact Activity를 호출한다.  
  + AddContact Activity  
AddContact Activity는 새로운 연락처를 추가하여 저장하는

+ ### Tab 2 사진첩  
<img src="https://user-images.githubusercontent.com/49242646/148055118-4b19e9f2-3532-4967-a333-6da336fc9f36.gif" width="205" height="411"/>



+ ### Tab 3 음악 플레이어  


+ ### Font  
        완도희망체 Bold  
        프리텐다드   
        카페24 써라운드에어  
