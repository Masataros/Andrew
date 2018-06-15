Sub SendGmail()

    '変数設定
    
    Dim cdoM, cdoC As Object
    Dim body, subject, ReTo As String
    Dim I As Integer
    I = 1
    
    
    'CDOの設定
    
    Set cdoM = CreateObject("CDO.Message")
    Set cdoC = CreateObject("CDO.Configuration")
    
    cdoC.Load -1
    
    With cdoC.Fields
        .Item("http://schemas.microsoft.com/cdo/configuration/sendusing") = 2
        .Item("http://schemas.microsoft.com/cdo/configuration/smtpserver") = "smtp.gmail.com"
        .Item("http://schemas.microsoft.com/cdo/configuration/smtpserverport") = "465"
        .Item("http://schemas.microsoft.com/cdo/configuration/smtpauthenticate") = True
        .Item("http://schemas.microsoft.com/cdo/configuration/smtpusessl") = True
        .Item("http://schemas.microsoft.com/cdo/configuration/sendusername") = "メールアドレス"
        .Item("http://schemas.microsoft.com/cdo/configuration/sendpassword") = "パスワード"
        .Update
    End With
    

    'ループでメールを作成
    
    Do While Cells(I, 1).Value <> ""
    
    ReTo = Replace(Cells(I, 1), "o3", "gmail")
    
    With cdoM
    
        Set .Configuration = cdoC
        .From = "アドレス"
        .to = ReTo
        .subject = "新規サービスのお知らせ"
        .TextBody = "貴方のユーザ名は" & Cells(I, 1) & vbNewLine & "初期パスワードは" & Cells(I, 2)
        .send
        
        I = I + 1
        
    End With
    
    
    Loop


End Sub
