# **Image Machine**

***Image Machine*** is an Android Application that helps grouping images from photo gallery and also easily access the image data.



## **Features**

In Image Machine, you can:

- See the list of inputted data and sort it by name or type.
- Create, update, and delete data.
- See the detailed information of certain data.
- Scan QR Code to retrieve certain data.



## **Library**

In this project, I used these libraries:

- RecyclerView.
- CardView.
- Glide.
- Room Persistence.



## **Usage**

#### **MainActivity**

[MainActivity](https://drive.google.com/file/d/1ghFI318QHqEC0a1wEAs6atW7ViTFgCpJ/view?usp=sharing)

Explanation:

1 &#8594; Machine Data Button &#8594; Direct user to MachineDataActivity.

2 &#8594; Code Reader Button &#8594; Direct user to CodeReaderActivity.



**MachineDataActivity**

[MachineDataActivity](https://drive.google.com/file/d/13N02UdIKntUc-lb0aXwlQEwpR2dmRejN/view?usp=sharing)

Explanation:

1 &#8594; Add Machine Data Button &#8594; Direct user to AddMachineDataActivity.

2 &#8594; Machine Data RecyclerView &#8594; Display the data with form of scrollable list.

3 &#8594; Machine Data Item &#8594; If the item is clicked, then it will direct user to MachineDataDetailActivity, but if the item is long clicked, then it will direct user to UpdateMachineDataActivity.

4 &#8594; Back Button &#8594; Direct user to previous activity.

5 &#8594; Sort Menu &#8594; Display the options to sort item by name or by type.

[MachineDataActivityWithMenu](https://drive.google.com/file/d/1HI6GylqK1v-ZjrJlW8PZ0ydQ20NDSJM1/view?usp=sharing)

Explanation:

1 &#8594; Sort By Name Menu &#8594; Sort item in the RecyclerView by name of the items alphabetically.

2 &#8594; Sort By Type Menu &#8594;Sort item in the RecyclerView by type of the items alphabetically.



**AddMachineDataActivity**

[AddMachineDataActivity](https://drive.google.com/file/d/1rBN1FBuhNMk_kvzpRrJcpUoNIY0ajYMC/view?usp=sharing)

Explanation:

1 &#8594; Add Machine ID EditText &#8594; Provide text field for inputting machine's ID value. But, in this project, the EditText is unedittable and the value is generated random String value.

2 &#8594; Add Machine Name EditText &#8594; Provide text field for inputting machine's name value. 

3 &#8594; Add Machine Type EditText &#8594; Provide text field for inputting machine's type value. 

4 &#8594; Add Machine QR Code Number EditText &#8594; Provide text field for inputting machine's QR Code Number value.  QR Code number value maximal length is 6.

5 &#8594; Add Latest Maintenance Date TextView &#8594; Set and display the machine latest maintenance date. The value is retrieved by using DatePicker.

6 &#8594; Add Machine Data Button &#8594; Insert the inputted data into Database and direct the user back into MachineDataActivity.

7 &#8594; Back Button &#8594; Direct user to previous activity.



**UpdateMachineDataActivity**

[UpdateMachineDataActivity](https://drive.google.com/file/d/10IrpnWZwKZKbowFAO-ZPwH2dNaa4IES4/view?usp=sharing)

Explanation:

1 &#8594; Update Machine ID EditText &#8594; Provide text field for updating machine's ID value. But the ID will be static, and cannot be changed.

2 &#8594; Update Machine Name EditText &#8594; Provide text field for updating machine's name value. 

3 &#8594; Update Machine Type EditText &#8594; Provide text field for updating machine's type value. 

4 &#8594; Update Machine QR Code Number EditText &#8594; Provide text field for updating machine's QR Code Number value. 

5 &#8594; Update Latest Maintenance Date TextView &#8594; Update and display the machine latest maintenance date. The value is retrieved by using DatePicker.

6 &#8594; Update Machine Data Button &#8594; Updated data into Database and direct the user back into MachineDataActivity.

7 &#8594; Back Button &#8594; Direct user to previous activity.



**MachineDataDetailActivity**

[MachineDataDetailActivity](https://drive.google.com/file/d/1SbhIXRAE1QR-THcElep6HhMus0fYssHw/view?usp=sharing)

Explanation:

1 &#8594; Machine Name Detail TextView &#8594; Display the machine name.

2 &#8594; Machine ID Detail TextView &#8594; Display the machine ID.

3 &#8594; Machine Type Detail TextView &#8594; Display the machine type.

4 &#8594; Machine QR Code Number Detail TextView &#8594; Display the machine QR Code number. 

5 &#8594; Machine Latest Maintenance Date Detail TextView &#8594; Display the machine latest maintenance date.

6 &#8594; Add Machine Image Button &#8594; Retrieve images from photo gallery after the user already gave permission to the application to read storages.

7 &#8594; Machine Image RecyclerView &#8594; Display the image data with form of scrollable list. The item inside RecyclerView will be ab

8 &#8594; Delete Machine Data Button &#8594; Delete the data from Database and direct the user back into MachineDataActivity.

9 &#8594; Back Button &#8594; Direct user to previous activity.

[MachineDataDetailActivityWithImages](https://drive.google.com/file/d/1QAtxss7B7IS1tJW8ugTI9Zi3XlTACumd/view?usp=sharing)

Explanation:

1 &#8594; Machine Image Item &#8594; If the item is clicked, then it will fetch the item data and direct user to ViewImageActivity.



**ViewImageActivity**

[ViewImageActivity](https://drive.google.com/file/d/1QAtxss7B7IS1tJW8ugTI9Zi3XlTACumd/view?usp=sharing)

Explanation:

1 &#8594; View Image ImageView &#8594; Display the image that retrieved from MachineDataDetailActivity Recyclerview item.



**CodeReaderActivity**

[CodeReaderActivity](https://drive.google.com/file/d/1iwr1_wP-iiIAFURqRAmXtM1ajwpisy8m/view?usp=sharing)

Explanation:

1 &#8594; Code Reader SurfaceView &#8594; Set as camera preview in the application and the view is only able to scan QR Code formats. If the scanned QR Code value is available in Database then, it will direct user to MachineDataDetailActivity with value of item that have same value with of scanned QR Code.
