USE [MobileBank]
GO
/****** Object:  User [ikfl]    Script Date: 11/25/2014 05:00:18 ******/
CREATE USER [ikfl] FOR LOGIN [ikfl] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  Table [dbo].[Phone_QuickServices]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [dbo].[Phone_QuickServices](
	[id] [int] NOT NULL,
	[PhoneNumber] [varchar](20) NOT NULL,
	[QuickServices_Status] [int] NOT NULL,
 CONSTRAINT [id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[mobile_operators]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mobile_operators](
	[id] [int] NOT NULL,
 CONSTRAINT [PK_operators] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MBCast]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MBCast](
	[PhoneNumber] [varchar](20) NOT NULL,
	[ChangeType] [char](1) NOT NULL,
	[ChangeDate] [date] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[UpdateSamples]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UpdateSamples](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[cardnum] [varchar](20) NULL,
	[phonenum] [varchar](20) NULL,
	[update] [varchar](8000) NULL,
	[error] [varchar](8000) NULL,
 CONSTRAINT [PK_UpdateSamples] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  StoredProcedure [dbo].[sp_raise_last_error]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[sp_raise_last_error]
AS
BEGIN
	DECLARE @NUM AS INT,
			@MSG AS VARCHAR(512),
			@SEV AS INT,
			@STATE AS INT,
			@PROC AS VARCHAR(256),
			@LINE AS INT

	SELECT	@NUM=ERROR_NUMBER(),
			@MSG=ERROR_MESSAGE(),
			@SEV=ERROR_SEVERITY(),
			@STATE=ERROR_STATE(),
			@PROC=ERROR_PROCEDURE(),
			@LINE=ERROR_LINE()
	RAISERROR(60000, @SEV, @STATE, @NUM, @SEV, @STATE, @PROC, @LINE, @MSG)
END
GO
/****** Object:  Table [dbo].[smses_imsi]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [dbo].[smses_imsi](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[date] [datetime2](7) NOT NULL,
	[externalSystemID] [int] NOT NULL,
	[mobileOperatorID] [int] NULL,
	[phoneNumber] [varchar](20) NOT NULL,
	[messageBody] [varchar](500) NOT NULL,
 CONSTRAINT [PK_smses_imsi] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ERMB_DisconnectedPhones]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ERMB_DisconnectedPhones](
	[DisconnectedPhoneID] [int] IDENTITY(1,1) NOT NULL,
	[PhoneNumber] [varchar](20) NOT NULL,
	[Reason] [tinyint] NOT NULL,
	[DisconnectedPhoneSource] [tinyint] NOT NULL,
	[CreatedAt] [datetime] NOT NULL,
	[ReceivedAt] [datetime] NULL,
	[ERIBState] [bit] NOT NULL,
	[ERIBProcTime] [datetime] NULL,
 CONSTRAINT [PK_ERMB_DisconnectedPhones] PRIMARY KEY CLUSTERED 
(
	[DisconnectedPhoneID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ERMB_ManagePhoneResult]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ERMB_ManagePhoneResult](
	[Element] [varchar](50) NOT NULL,
	[ErrorValue] [varchar](50) NOT NULL,
	[Format] [varchar](50) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ERMB_GetRegistrations]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ERMB_GetRegistrations](
	[RegTranType] [varchar](10) NOT NULL,
	[RegVersionID] [varchar](2) NOT NULL,
	[PhoneNumber] [varchar](20) NOT NULL,
	[PP] [varchar](15) NULL,
	[IP1] [varchar](50) NULL,
	[IP2] [varchar](50) NULL,
	[IP3] [varchar](50) NULL,
	[IP4] [varchar](50) NULL,
	[IP5] [varchar](50) NULL,
	[ActiveCard1] [varchar](20) NULL,
	[ActiveCard2] [varchar](20) NULL,
	[ActiveCard3] [varchar](20) NULL,
	[ActiveCard4] [varchar](20) NULL,
	[ActiveCard5] [varchar](20) NULL,
	[ActiveCard6] [varchar](20) NULL,
	[ActiveCard7] [varchar](20) NULL,
	[ActiveCard8] [varchar](20) NULL,
	[Bank] [char](2) NOT NULL,
	[TarifType] [tinyint] NULL,
	[RegAction] [char](1) NOT NULL,
	[Branch] [char](4) NOT NULL,
	[PaymentCardNumber] [varchar](20) NOT NULL,
	[Agency] [char](4) NOT NULL,
	[FiltrationReasonName] [varchar](50) NOT NULL,
	[Passport] [varchar](19) NULL,
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[TryCount] [int] NOT NULL,
 CONSTRAINT [PK_ERMB_GetRegistrations] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ermb_accepted_registrations]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ermb_accepted_registrations](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[PhoneNumber] [varchar](20) NOT NULL,
	[ConnectedDate] [datetime2](7) NOT NULL,
 CONSTRAINT [PK_ermb_accepted_registrations] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[card_smses]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[card_smses](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[date] [datetime2](7) NOT NULL,
	[externalSystemID] [int] NOT NULL,
	[SMSID] [int] NOT NULL,
	[CardNumber] [varchar](20) NOT NULL,
	[messageBody] [varchar](500) NOT NULL,
	[IgnoreBlockStatus] [bit] NOT NULL,
 CONSTRAINT [PK_card_smses] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[BeginMigration]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[BeginMigration](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[CardList] [varchar](max) NULL,
	[PhoneListToMigrate] [varchar](max) NULL,
	[PhoneListToDelete] [varchar](max) NULL,
	[MigrationID] [bigint] NULL,
	[ErrNumber] [int] NOT NULL,
	[strErrDescr] [varchar](500) NOT NULL,
	[LinkID] [int] NOT NULL,
	[PhoneNumber] [varchar](11) NOT NULL,
	[PaymentCard] [varchar](20) NOT NULL,
	[InfoCards] [varchar](8000) NULL,
	[LinkTariff] [int] NOT NULL,
	[LinkPaymentBlockID] [int] NOT NULL,
	[PhoneBlockCode] [int] NOT NULL,
	[PhoneQuickServices] [int] NOT NULL,
	[PhoneReklama] [int] NOT NULL,
	[PhoneFirstRegistration] [datetime] NOT NULL,
	[PhoneActivityVolume] [int] NOT NULL,
	[LinkFirstRegistration] [datetime] NOT NULL,
	[PhoneOffers] [varchar](8000) NULL,
	[Templates] [varchar](8000) NULL,
	[PaymentPeriodBegin] [datetime] NULL,
	[PaymentPeriodEnd] [datetime] NULL,
	[PaymentAmount] [money] NULL,
	[PaymentCurrency] [varchar](3) NULL,
	[PaymentType] [varchar](1) NULL,
	[PaymentStatus] [int] NULL,
 CONSTRAINT [PK_ERMB_BeginMigration] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ERMB_RegistrationResult]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ERMB_RegistrationResult](
	[ID] [bigint] NOT NULL,
	[ResultCode] [int] NOT NULL,
	[ErrorDescr] [varchar](50) NULL,
 CONSTRAINT [PK_ERMB_RegistrationResult] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ermb_payment_card_result]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ermb_payment_card_result](
	[Id] [bigint] IDENTITY(1,1) NOT NULL,
	[MobilePaymentCardRequestID] [bigint] NOT NULL,
	[CardNumber] [varchar](20) NULL,
	[ClientName] [nvarchar](500) NULL,
	[ResultCode] [int] NOT NULL,
	[Comment] [varchar](200) NULL,
 CONSTRAINT [pk_ermb_payment_card_result] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ermb_payment_card_request]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ermb_payment_card_request](
	[MobilePaymentCardRequestID] [bigint] IDENTITY(1,1) NOT NULL,
	[PhoneNumber] [varchar](20) NOT NULL,
	[TerBank] [int] NOT NULL,
	[Branch] [int] NOT NULL,
	[CreatedAt] [datetime] NULL,
	[TryCount] [int] NOT NULL,
 CONSTRAINT [pk_ermb_payment_card_request] PRIMARY KEY CLUSTERED 
(
	[MobilePaymentCardRequestID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  StoredProcedure [dbo].[mb_BATCH_ERMB_ConfirmRegistrationLoading]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_BATCH_ERMB_ConfirmRegistrationLoading]
                               @ID VARCHAR(MAX)=NOTNULL
AS
BEGIN
                return 0;
END
GO
/****** Object:  UserDefinedFunction [dbo].[IsDecimalString]    Script Date: 11/25/2014 05:00:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[IsDecimalString] 
(
	@STR AS VARCHAR(255)
)
RETURNS INT
AS
BEGIN
	DECLARE @LEN AS INT,
			@I AS INT,
			@C AS VARCHAR

	SET @LEN = LEN(@STR)

	SET @I=1
	WHILE @I<=@LEN BEGIN
		SET @C = SUBSTRING(@STR, @I, 1)
		IF CHARINDEX(@C, '1234567890')=0
			RETURN 0
		SET @I = @I+1
	END

	RETURN 1
END
GO
/****** Object:  Table [dbo].[internetOffers]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [dbo].[internetOffers](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ExternalSystemID] [int] NOT NULL,
	[MessageID] [int] NOT NULL,
	[PhoneNumber] [varchar](20) NOT NULL,
	[MessageText] [varchar](500) NOT NULL,
	[RECEIPT_TIME] [datetime] NULL,
 CONSTRAINT [PK_internetOffers] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GetSamples]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GetSamples](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[cardnum] [varchar](20) NOT NULL,
	[phonenum] [varchar](20) NULL,
	[retstr] [varchar](8000) NULL,
 CONSTRAINT [PK_samples] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UNIQ_samples] UNIQUE NONCLUSTERED 
(
	[cardnum] ASC,
	[phonenum] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GetRegistrations2]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GetRegistrations2](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[cardnum] [varchar](20) NOT NULL,
	[retstr] [varchar](8000) NULL,
 CONSTRAINT [PK_registrations2] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GetRegistrations]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GetRegistrations](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[cardnum] [varchar](20) NOT NULL,
	[retstr] [varchar](8000) NULL,
 CONSTRAINT [PK_registrations] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GetIMSIResult]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [dbo].[GetIMSIResult](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[messId] [bigint] NOT NULL,
	[phone] [varchar](20) NOT NULL,
	[result] [int] NOT NULL,
 CONSTRAINT [PK_GetIMSIResult] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GetClientByCardNumber]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [dbo].[GetClientByCardNumber](
	[CardNumber] [varchar](20) NOT NULL,
	[FirstName] [varchar](100) NOT NULL,
	[FathersName] [varchar](100) NULL,
	[LastName] [varchar](100) NOT NULL,
	[RegNumber] [varchar](20) NOT NULL,
	[BirthDate] [datetime] NOT NULL,
	[CbCode] [varchar](20) NOT NULL,
	[AuthIdt] [varchar](10) NOT NULL,
	[ContrStatus] [int] NOT NULL,
	[AddInfoCn] [int] NOT NULL,
	[id] [int] NOT NULL,
	[Password] [varchar](65) NOT NULL,
 CONSTRAINT [GetClientByCardNumber_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [GetClientByCardNumber_AuthIdt_UNIQ] UNIQUE NONCLUSTERED 
(
	[AuthIdt] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [GetClientByCardNumber_CardNumber_UNIQ] UNIQUE NONCLUSTERED 
(
	[CardNumber] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GetCardsByPhone]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GetCardsByPhone](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[PhoneNumber] [varchar](11) NOT NULL,
	[CardNumber] [varchar](20) NOT NULL,
 CONSTRAINT [PK_ERMB_GetCardsByPhone] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GetCardByPhone]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [dbo].[GetCardByPhone](
	[id] [int] NOT NULL,
	[phone] [varchar](20) NOT NULL,
	[terBank] [tinyint] NOT NULL,
	[branch] [smallint] NOT NULL,
	[card] [varchar](20) NOT NULL,
	[error] [int] NOT NULL,
	[comment] [nvarchar](2000) NOT NULL,
	[retCode] [int] NOT NULL,
	[clientName] [nvarchar](500) NOT NULL,
 CONSTRAINT [PK_cardByPhone] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  UserDefinedFunction [dbo].[fn_ParseText2Table]    Script Date: 11/25/2014 05:00:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/****** Object:  StoredProcedure [dbo].[mb_MockChangeIPasPasswordByLoginPassword]******/


create function [dbo].[fn_ParseText2Table]
    (
    @p_SourceText  varchar(8000)
    ,@p_Delimeter varchar(100) = ',' --default to comma delimited.
    )
RETURNS @retTable TABLE
    (
    Position  int identity(1,1)
    ,txt_value varchar(2000)
    )
AS
/* Функция для разбиения строки на токены, используя указанный разделитель.
   Источник: http://www.codeproject.com/Articles/7938/SQL-User-Defined-Function-to-Parse-a-Delimited-Str
********************************************************************************
Purpose: Parse values from a delimited string
  & return the result as an indexed table
Copyright 1996, 1997, 2000, 2003 Clayton Groom (<A href="mailto:Clayton_Groom@hotmail.com">Clayton_Groom@hotmail.com</A>)
Posted to the public domain Aug, 2004
06-17-03 Rewritten as SQL 2000 function.
 Reworked to allow for delimiters > 1 character in length
 and to convert Text values to numbers
********************************************************************************
*/
BEGIN
    DECLARE @w_Continue  int
        ,@w_StartPos  int
        ,@w_Length  int
        ,@w_Delimeter_pos int
        ,@w_tmp_txt   varchar(2000)
        ,@w_Delimeter_Len tinyint
    if len(@p_SourceText) = 0
    begin
        SET @w_Continue = 0 -- force early exit
    end
    else
    begin
        -- parse the original @p_SourceText array into a temp table
        SET  @w_Continue = 1
        SET @w_StartPos = 1
        SET @p_SourceText = RTRIM( LTRIM( @p_SourceText))
        SET @w_Length   = DATALENGTH( RTRIM( LTRIM( @p_SourceText)))
        SET @w_Delimeter_Len = len(@p_Delimeter)
    end
    WHILE @w_Continue = 1
    BEGIN
        SET @w_Delimeter_pos = CHARINDEX( @p_Delimeter
            ,(SUBSTRING( @p_SourceText, @w_StartPos
            ,((@w_Length - @w_StartPos) + @w_Delimeter_Len)))
        )

        IF @w_Delimeter_pos > 0  -- delimeter(s) found, get the value
        BEGIN
            SET @w_tmp_txt = LTRIM(RTRIM( SUBSTRING( @p_SourceText, @w_StartPos
                ,(@w_Delimeter_pos - 1)) ))
            SET @w_StartPos = @w_Delimeter_pos + @w_StartPos + (@w_Delimeter_Len- 1)
        END
        ELSE -- No more delimeters, get last value
        BEGIN
            SET @w_tmp_txt = LTRIM(RTRIM( SUBSTRING( @p_SourceText, @w_StartPos
                ,((@w_Length - @w_StartPos) + @w_Delimeter_Len)) ))
            SELECT @w_Continue = 0
        END
        INSERT INTO @retTable VALUES( @w_tmp_txt )
    END
    RETURN
END
GO
/****** Object:  StoredProcedure [dbo].[ERMB_RollbackMigration]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[ERMB_RollbackMigration]
                    @MigrationID bigint                   
AS
BEGIN   
     IF (@MigrationID = 0)
        RETURN 1307280002;
 
     DECLARE @isMigration AS INT
    
     SELECT @isMigration = count(*)
     FROM dbo.BeginMigration AS bm
     WHERE @MigrationID = bm.MigrationID
 
     IF (@isMigration = 0)
        RETURN 1307280001;

 
     RETURN 0;
END;
GO
/****** Object:  StoredProcedure [dbo].[ERMB_ReverseMigration]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[ERMB_ReverseMigration]
                    @MigrationID bigint                   
AS
BEGIN   
     IF (@MigrationID = 0)
        RETURN 1307290000;
 
     IF (@MigrationID = 1111)
        RETURN 1307291113;
 
     IF (@MigrationID = 2222)
        RETURN 1307291114;
 
     DECLARE @isMigration AS INT
    
     SELECT @isMigration = count(*)
     FROM dbo.BeginMigration AS bm
     WHERE @MigrationID = bm.MigrationID
 
     IF (@isMigration = 0)
        RETURN 1307291111;

 
     RETURN 0;
END;
GO
/****** Object:  StoredProcedure [dbo].[ERMB_MobilePaymentCardResult]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[ERMB_MobilePaymentCardResult]
                @MobilePaymentCardRequestId Bigint,
                @CardNumber Varchar(20),
                @ClientName NVarchar(500),
                @ResultCode Int,
                @Comment Varchar(200)
AS
BEGIN
 
                DECLARE @Count AS INT;
               
                select @Count = Count(*)
                from
                               ermb_payment_card_result
               
                IF @Count > 10000
                               delete from ermb_payment_card_result
               
               
                insert ermb_payment_card_result
                               (MobilePaymentCardRequestId, CardNumber, ClientName, ResultCode, Comment)
                values
                               (@MobilePaymentCardRequestId, @CardNumber, @ClientName, @ResultCode, @Comment)
               
                RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[ERMB_MobilePaymentCardRequests]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[ERMB_MobilePaymentCardRequests]
AS
BEGIN
    select * from ermb_payment_card_request where TryCount > 0;
 
    update ermb_payment_card_request
       set TryCount=TryCount-1
    where TryCount > 0;
END
GO
/****** Object:  StoredProcedure [dbo].[mb_CheckSendSms]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[mb_CheckSendSms]
	@CardNumber varchar(20)
AS
BEGIN
	DECLARE @RC AS INT

	SET @RC = -1;
	SELECT @RC=0
	FROM dbo.GetRegistrations
	WHERE cardnum=@CardNumber
	
	RETURN @RC;
END;
GO
/****** Object:  StoredProcedure [dbo].[mb_CheckPhoneNumber]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[mb_CheckPhoneNumber]
	@strPhoneNumber VARCHAR(20)
AS
BEGIN
	IF LEN(@strPhoneNumber) > 11
		RAISERROR(50015, 16, 1, @strPhoneNumber)
		
	IF SUBSTRING (@strPhoneNumber, 1, 1)<>'7'
		RAISERROR(50017, 16, 1, @strPhoneNumber)

	IF 0=dbo.IsDecimalString(@strPhoneNumber)
		RAISERROR(50016, 16, 1, @strPhoneNumber)
END
GO
/****** Object:  StoredProcedure [dbo].[mb_BATCH_ERMB_RegistrationResult]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_BATCH_ERMB_RegistrationResult]
         @ID BIGINT=NOTNULL,
         @ResultCode INT = NOTNULL,
         @ErrorDescr VARCHAR(50) = NULL
AS
BEGIN
 
      DECLARE @resId AS BIGINT;
 
      SELECT @resId = rr.id
      FROM dbo.ERMB_RegistrationResult AS rr
      WHERE  id = @ID;
 
        IF (@resId is null)
       BEGIN
           insert ERMB_RegistrationResult
           (ID, ResultCode, ErrorDescr)
        values
           (@ID, @ResultCode, @ErrorDescr);
 
        return 0;
       END;
 
       UPDATE dbo.ERMB_RegistrationResult
       SET ResultCode = @ResultCode, ErrorDescr = @ErrorDescr
       WHERE id = @ID;
 
       return 0;
END
GO
/****** Object:  StoredProcedure [dbo].[mb_BATCH_ERMB_ManagePhones]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_BATCH_ERMB_ManagePhones]
                               @PhonesArray VARCHAR(MAX) = NOTNULL
AS
BEGIN
                select * from dbo.ERMB_ManagePhoneResult
                return 0;
END
GO
/****** Object:  StoredProcedure [dbo].[mb_BATCH_ERMB_GetRegistrations]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[mb_BATCH_ERMB_GetRegistrations]
AS
BEGIN
	DECLARE @retcode int = 0;
	
    SET NOCOUNT ON;
               
    select * from ERMB_GetRegistrations
    where TryCount > 0;
           
    IF @@ROWCOUNT = 0
      SET @retcode = -1;
      
    update ERMB_GetRegistrations
       set TryCount=TryCount-1
    where TryCount > 0;
                                           
    return @retcode;
END
GO
/****** Object:  StoredProcedure [dbo].[ERMB_GetCardsByPhone]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[ERMB_GetCardsByPhone]
                @PhoneNumber varchar(20)               
AS
BEGIN
        IF (LEN(@PhoneNumber) > 11 or  SUBSTRING (@PhoneNumber, 1, 1)<>'7') BEGIN
            return 1307190001;
        END;
 
        IF (@PhoneNumber = 12345678910) BEGIN
            return 1307190002;
        END;
 
        select
            phone.CardNumber    
        from
            dbo.GetCardsByPhone AS phone
        where
            PhoneNumber = @PhoneNumber;
 
       RETURN 0;
END;
GO
/****** Object:  StoredProcedure [dbo].[mb_WWW_GetClientByLogin]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_WWW_GetClientByLogin]
    @AuthIdt VARCHAR(10),                     --логин СБОЛ
    @FirstName VARCHAR(100) OUTPUT,           --имя
    @FathersName VARCHAR(100) OUTPUT,         --отчество
    @LastName VARCHAR(100) OUTPUT,            --фамилия
    @RegNumber VARCHAR(20) OUTPUT,            --паспортные данные
    @BirthDate DATE OUTPUT,                   --дата рождения
    @CbCode  VARCHAR(20) OUTPUT,              --тербанк в котором выдана карта (полный формат)
    @CardNumber VARCHAR(20) OUTPUT,           --номер карты
    @ContrStatus INT  OUTPUT,                 --cтатус контракта
    @AddInfoCn INT OUTPUT,                    --признак основная=1/дополнительная карта=2
    @strErrDescr VARCHAR(500)= NULL OUTPUT    --описание ошибки 
AS
BEGIN
    DECLARE @Id AS INT;
    IF (@AuthIdt='1234567890') BEGIN
        SET @strErrDescr ='Ошибочка вышла'; 
        return 999;    
    END 
    SELECT
        @Id            = tbl.id,  
        @FirstName     = tbl.FirstName,
        @FathersName   = tbl.FathersName,
        @LastName      = tbl.LastName,
        @RegNumber     = tbl.RegNumber,
        @BirthDate     = tbl.BirthDate,
        @CbCode        = tbl.CbCode,
        @CardNumber    = tbl.CardNumber,
        @ContrStatus   = tbl.ContrStatus,
        @AddInfoCn     = tbl.AddInfoCn  
    FROM 
        dbo.GetClientByCardNumber as tbl
    WHERE 
        tbl.AuthIdt = @AuthIdt;
    IF (@Id IS NULL) BEGIN
        SET @strErrDescr = 'Данные о пользователе не найдены.';
        RETURN -100;
    END;
END
GO
/****** Object:  StoredProcedure [dbo].[mb_WWW_GetClientByCardNumber]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_WWW_GetClientByCardNumber]
    @CardNumber VARCHAR(20),                  --номер карты
    @FirstName VARCHAR(100) OUTPUT,           --имя
    @FathersName VARCHAR(100) OUTPUT,         --отчество
    @LastName VARCHAR(100) OUTPUT,            --фамилия
    @RegNumber VARCHAR(20) OUTPUT,            --паспортные данные
    @BirthDate DATE OUTPUT,                   --дата рождения
    @CbCode  VARCHAR(20) OUTPUT,              --тербанк в котором выдана карта (полный формат)
    @AuthIdt VARCHAR(10) OUTPUT,              --логин СБОЛ
    @ContrStatus INT  OUTPUT,                 --cтатус контракта
    @AddInfoCn INT OUTPUT,                    --признак основная=1/дополнительная карта=2
    @strErrDescr VARCHAR(500)= NULL OUTPUT    --описание ошибки
AS
 
BEGIN
    DECLARE @Id AS INT;
    IF (@CardNumber='8888888888888888') BEGIN
        SET @strErrDescr ='Ошибочка вышла';
        return 999;   
    END;
    IF (SUBSTRING(@CardNumber, 1, 5) = '99999') BEGIN
        SET @strErrDescr ='Карта не сбербанка';
        return 50014;   
    END;
    SELECT
        @Id            = tbl.id, 
        @FirstName     = tbl.FirstName,
        @FathersName   = tbl.FathersName,
        @LastName      = tbl.LastName,
        @RegNumber     = tbl.RegNumber,
        @BirthDate     = tbl.BirthDate,
        @CbCode        = tbl.CbCode,
        @AuthIdt       = tbl.AuthIdt,
        @ContrStatus   = tbl.ContrStatus,
        @AddInfoCn     = tbl.AddInfoCn 
    FROM
        dbo.GetClientByCardNumber as tbl
    WHERE
       tbl.CardNumber = @CardNumber;
 
    IF (@Id IS NULL) BEGIN
        SET @strErrDescr = 'Данные о пользователе не найдены.';
        RETURN -100;
    END;
END
GO
/****** Object:  StoredProcedure [dbo].[mb_sendSmsByCardNumber]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[mb_sendSmsByCardNumber]
	@iExternalSystemID int,
	@SMSID int,
	@CardNumber varchar(20),
	@vbMessageBody varbinary(500),
	@IgnoreBlockStatus bit
AS
BEGIN
	DECLARE @RC AS INT

	IF 0=dbo.IsDecimalString(@CardNumber)
		RAISERROR(50016, 16, 1, @CardNumber)
	
	BEGIN TRY
		BEGIN TRANSACTION;
		INSERT INTO card_smses
		VALUES (CURRENT_TIMESTAMP,
				@iExternalSystemID,
				@SMSID,
				@CardNumber,
				@vbMessageBody,
				@IgnoreBlockStatus
		)
	    COMMIT TRANSACTION
		RETURN 0
	END TRY
	BEGIN CATCH
		ROLLBACK TRANSACTION
		SET @RC = ERROR_NUMBER()
	
		IF (@RC = 2601)
		BEGIN
			RAISERROR(50309, 16, 1, @iExternalSystemID, @SMSID)
			RETURN -4
		END
	
		EXEC sp_raise_last_error
		RETURN -1
    END CATCH 
END;
GO
/****** Object:  StoredProcedure [dbo].[mb_Phone_QuickServices_SetStatus]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_Phone_QuickServices_SetStatus]
    @strPhoneNumber          varchar(20),
    @iExternalSystemID       int,
    @QuickServices_NewStatus int
AS
BEGIN
    IF (@strPhoneNumber IS NULL OR @iExternalSystemID IS NULL)
       RETURN -1;

	DECLARE @RC AS INT;

	SET @RC = 1204092000;

    SELECT @RC = 0
      FROM dbo.Phone_QuickServices
     WHERE
           @strPhoneNumber        = PhoneNumber AND
        @iExternalSystemID        = 1           AND
        @QuickServices_NewStatus != QuickServices_Status;

    IF @RC = 0
      BEGIN
        UPDATE dbo.Phone_QuickServices
        SET QuickServices_Status = @QuickServices_NewStatus
        WHERE PhoneNumber = @strPhoneNumber;
        RETURN 0;
      END;

    SELECT @RC = CASE QuickServices_Status
                 WHEN 0 THEN 1204091020
                 WHEN 1 THEN 1209091010
                 END
    FROM dbo.Phone_QuickServices
    WHERE
        @strPhoneNumber           = PhoneNumber          AND
        @iExternalSystemID        = 1                    AND
        @QuickServices_NewStatus  = QuickServices_Status
    RETURN @RC
END
GO
/****** Object:  StoredProcedure [dbo].[mb_Offer_SendMessage]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_Offer_SendMessage]
    @ExternalSystemID  int,
    @MessageID         int,
    @PhoneNumber       varchar(20),
    @MessageText       varchar(500)
AS
BEGIN
    INSERT INTO [dbo].[internetOffers] ([ExternalSystemID], [MessageID], [PhoneNumber], [MessageText])
    VALUES (@ExternalSystemID, @MessageID, @PhoneNumber, @MessageText);

    RETURN 0;
END;
GO
/****** Object:  StoredProcedure [dbo].[mb_Offer_Quit]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_Offer_Quit]
    @ExternalSystemID  int,
    @MessageID         int      
AS
BEGIN
    IF (@ExternalSystemID IS NULL) BEGIN
        return -1;
    END;
 
    DELETE FROM
        dbo.internetOffers
    WHERE
        ExternalSystemID = @ExternalSystemID AND
        MessageID = @MessageID;
 
    RETURN 0;
END;
GO
/****** Object:  StoredProcedure [dbo].[mb_Offer_Confirm]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_Offer_Confirm]
    @ExternalSystemID  int
AS
BEGIN
    IF (@ExternalSystemID IS NULL) BEGIN
        return -1;
    END;
 
    SELECT
        offer.MessageID, offer.RECEIPT_TIME
    FROM
        dbo.internetOffers AS offer
    WHERE
        ExternalSystemID = @ExternalSystemID AND
        offer.RECEIPT_TIME IS NOT NULL;
 
    RETURN 0;
END;
GO
/****** Object:  StoredProcedure [dbo].[mb_MockGetClientByLoginPassword]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_MockGetClientByLoginPassword] 
    @AuthIdt          varchar(20),
    @InputPassword    varchar(65),
    @CardNumber       varchar(20)  output,
    @FirstName        varchar(100) output,
    @FathersName      varchar(100) output,
    @LastName         varchar(100) output,
    @RegNumber        varchar(20)  output,
    @BirthDate        date         output,
    @CbCode           varchar(20)  output,
    @strErrDescr      varchar(500) output
AS
BEGIN
    DECLARE @Id INT, @Password AS VARCHAR(65);
    
    IF (@AuthIdt = '0123456789' AND @InputPassword = '0123456789') BEGIN
        SET @strErrDescr ='Неизвестная ошибка'; 
        RETURN 999;    
    END; 

    SELECT
        @Id            = tbl.id,  
        @FirstName     = tbl.FirstName,
        @FathersName   = tbl.FathersName,
        @LastName      = tbl.LastName,
        @RegNumber     = tbl.RegNumber,
        @BirthDate     = tbl.BirthDate,
        @CbCode        = tbl.CbCode,
        @CardNumber    = tbl.CardNumber,  
        @Password      = tbl.Password
    FROM 
        dbo.GetClientByCardNumber as tbl
    WHERE 
        tbl.AuthIdt = @AuthIdt;
        
    IF (@Id IS NULL)
    BEGIN
        SET @strErrDescr = 'Данные о пользователе не найдены.';
        RETURN -100;
    END;

    IF (@InputPassword != @Password)
    BEGIN
        SET @strErrDescr = 'Неверный пароль.';
        RETURN -200;
    END;
END
GO
/****** Object:  StoredProcedure [dbo].[mb_MockChangeIPasPasswordByLoginPassword]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/****** Object:  StoredProcedure [dbo].[mb_WWW_GetRegistrations2]******/



/****** Object:  StoredProcedure [dbo].[mb_MockChangeIPasPasswordByLoginPassword]******/
CREATE PROCEDURE [dbo].[mb_MockChangeIPasPasswordByLoginPassword] 
    @AuthIdt          varchar(20),
    @InputPassword    varchar(65),
    @NewPassword      varchar(65)  output,
    @strErrDescr      varchar(500) output
AS
BEGIN
    DECLARE @Id INT, @Password AS VARCHAR(65), @CardNumber AS VARCHAR(256);
    
    IF (@AuthIdt = '0123456789' AND @InputPassword = '0123456789') BEGIN
        SET @strErrDescr ='Неизвестная ошибка'; 
        RETURN 999;    
    END;

    SELECT
        @Id            = tbl.id,    
        @CardNumber    = tbl.CardNumber,  
        @Password      = tbl.Password
    FROM 
        dbo.GetClientByCardNumber as tbl
    WHERE 
        tbl.AuthIdt = @AuthIdt;
        
    IF (@Id IS NULL)
    BEGIN
        SET @strErrDescr = 'Данные о пользователе не найдены.';
        RETURN -100;
    END;

    IF (@InputPassword != @Password)
    BEGIN
        SET @strErrDescr = 'Неверный пароль.';
        RETURN -200;
    END;

    SET @NewPassword = SUBSTRING(REPLACE(NEWID(), '-', ''),0,9);
    
    UPDATE dbo.GetClientByCardNumber 
        SET Password = @NewPassword
    WHERE 
        AuthIdt = @AuthIdt; 
END;
GO
/****** Object:  StoredProcedure [dbo].[mb_IMSI_SendSMS]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[mb_IMSI_SendSMS]
	@strPhoneNumber varchar(20),
	@iExternalSystemID int,
	@strSMS varchar(500),
	@strStubSMS varchar(500),
	@iMobileOperator tinyint,
	@iMessageID bigint OUTPUT
AS
BEGIN
    DECLARE @messagId int;
    select @messagId = messId from dbo.GetIMSIResult where phone = @strPhoneNumber;
    IF @messagId IS NULL
        return 999;
    ELSE
    BEGIN 
        BEGIN TRANSACTION;
        INSERT INTO smses_imsi
            VALUES (CURRENT_TIMESTAMP,
				@iExternalSystemID,
				@iMobileOperator,				
				@strPhoneNumber,
				@strSMS
			)        
        COMMIT TRANSACTION;
        SET @iMessageID = @messagId;
        return 0;
    END;
END
GO
/****** Object:  StoredProcedure [dbo].[mb_IMSI_GetValidationResult]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[mb_IMSI_GetValidationResult]
	@iMessageID bigint
AS
BEGIN

	select phone, 
	       result
	from dbo.GetIMSIResult
	where messId = @iMessageID;

	
	DECLARE @id int;
	select @id = id from dbo.GetIMSIResult where messId = @iMessageID;
    	IF @id IS NULL
	    return 1202084008;
	ELSE IF @iMessageID = 38
	    return 1202084009;	
    	ELSE
	    return 0;
END
GO
/****** Object:  StoredProcedure [dbo].[ERMB_CommitMigration]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[ERMB_CommitMigration]
                    @MigrationID bigint                   
AS
BEGIN   
    if (@MigrationID = 0)
        RETURN 1307280001;
 
    SELECT bm.LinkID,bm.PhoneNumber,bm.PaymentCard,bm.PaymentPeriodBegin,
           bm.PaymentPeriodEnd,bm.PaymentAmount,bm.PaymentCurrency,bm.PaymentType,
           bm.PaymentStatus,bm.LinkPaymentBlockID
    FROM BeginMigration bm
    WHERE @MigrationID = bm.MigrationID
 
    RETURN 0;

END;
GO
/****** Object:  StoredProcedure [dbo].[ERMB_BeginMigration]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[ERMB_BeginMigration]
                  @CardList VARCHAR(MAX)=NULL,
                  @PhoneListToMigrate VARCHAR(MAX)=NULL,
                  @PhoneListToDelete VARCHAR(MAX)=NULL,
                  @MigrationID BIGINT OUTPUT,
                  @ErrNumber INT OUTPUT,
                  @strErrDescr VARCHAR(MAX)=NULL OUTPUT          
AS
BEGIN   
        
      SET @MigrationID = 0; 
  
       SELECT 
           @ErrNumber    = bm.ErrNumber,
           @strErrDescr  = bm.strErrDescr
       FROM
           dbo.BeginMigration AS bm
       WHERE
           CardList = @CardList OR
           PhoneListToMigrate = @PhoneListToMigrate OR
           PhoneListToDelete = @PhoneListToDelete;
 
       if (@ErrNumber <> 0) BEGIN
           RETURN @ErrNumber;
       END       
       
       BEGIN TRANSACTION;
 
           INSERT INTO dbo.GlobalSequence
/*            OUTPUT inserted.id */
           DEFAULT VALUES
       
 
       SELECT @MigrationID = max(id)
       FROM dbo.GlobalSequence
 
       UPDATE dbo.BeginMigration
       SET MigrationID = @MigrationID
      WHERE CardList = @CardList  OR
             PhoneListToDelete = @PhoneListToDelete OR
             PhoneListToDelete = @PhoneListToDelete;
       COMMIT TRANSACTION;
 
       SELECT
           bm.LinkID, bm.PhoneNumber, bm.PaymentCard,
           bm.InfoCards, bm.LinkTariff, bm.LinkPaymentBlockID,
           bm.PhoneBlockCode, bm.PhoneQuickServices,
           bm.PhoneReklama, bm.PhoneFirstRegistration,   
           bm.PhoneActivityVolume, bm.LinkFirstRegistration,   
           bm.PhoneOffers, bm.Templates        
       FROM
           dbo.BeginMigration AS bm
       WHERE
           CardList = @CardList OR
           PhoneListToMigrate = @PhoneListToMigrate OR
           PhoneListToDelete = @PhoneListToDelete;
    
       RETURN  @ErrNumber ;
END;
GO
/****** Object:  Table [dbo].[smses]    Script Date: 11/25/2014 05:00:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[smses](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[date] [datetime2](7) NOT NULL,
	[externalSystemID] [int] NOT NULL,
	[mobileOperatorID] [int] NOT NULL,
	[SMSID] [int] NOT NULL,
	[phoneNumber] [varchar](20) NOT NULL,
	[messageBody] [varchar](500) NOT NULL,
 CONSTRAINT [PK_smses] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  StoredProcedure [dbo].[mb_WWW_UpdateSamples]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[mb_WWW_UpdateSamples]
	@strCardNumber VARCHAR(20),
	@strPhoneNumber VARCHAR(16),
	@strUpdString VARCHAR(8000),
	@iCounter int OUTPUT,
	@strErrorDescr VARCHAR(8000) OUTPUT
AS
BEGIN
/*
Обновление таблицы GetSamples
*/
	SET @iCounter = 1
	SET @strErrorDescr = ''

	SELECT @strErrorDescr = [error]
	FROM dbo.UpdateSamples
	WHERE cardnum = @strCardNumber
	AND phonenum = @strPhoneNumber
	AND [update] = @strUpdString
	
	IF (@strErrorDescr IS NULL) OR (@strErrorDescr = '')
	BEGIN
		DECLARE @DestListPos int,
				@MobileOperatorPos int,
				@RetStr VARCHAR(8000),
				@DestListText varchar(8000),
				@CommaPos int,
				@Recipient varchar(8000),
				@RecipientPos int,
				@Text varchar(8000),
				@TempPos int

		SELECT @RetStr = retstr
		FROM dbo.GetSamples
		WHERE cardnum = @strCardNumber
			AND phonenum = @strPhoneNumber
		
		-- нахождение позиций начала и окончания DESTLIST
		SET @DestListPos = CHARINDEX('DESTLIST', @RetStr)
		SET @MobileOperatorPos = CHARINDEX('MOBILE_OPERATOR', @RetStr)
	
		-- определение подстроки DESTLIST
		SET @DestListText = SUBSTRING(@RetStr, @DestListPos, @MobileOperatorPos - @DestListPos)

		-- позиция запятой
		SET @CommaPos = LEN('DESTLIST=') + 1
	
		WHILE @CommaPos != 1
		BEGIN
			IF CHARINDEX(':', @strUpdString, @CommaPos) != 0
			BEGIN
				-- нахождение поставщика в новой строке
				SET @Recipient = SUBSTRING(@strUpdString, @CommaPos,
					CHARINDEX(':', @strUpdString, @CommaPos + 1) - @CommaPos)
		
				-- нахождение следующего разделителя (',' или ';')
				SET @TempPos = CHARINDEX(',', @strUpdString, @CommaPos + 1)
				IF @TempPos = 0
					SET @TempPos = CHARINDEX(';', @strUpdString, @CommaPos + 1)
			
				-- определение текста поставщика, на который будем заменять
				SET @Text = SUBSTRING(@strUpdString,
					CHARINDEX(@Recipient, @strUpdString),
					@TempPos + 1 -
					CHARINDEX(@Recipient, @strUpdString) - 1)
		
				-- определение позиции поставщика в строке таблицы
				SET @RecipientPos = CHARINDEX(@Recipient, @DestListText)
			
				-- нахождение следующего разделителя (',' или ';')
				SET @TempPos = CHARINDEX(',', @DestListText, @RecipientPos)
				IF @TempPos = 0
					SET @TempPos = CHARINDEX(';', @DestListText, @RecipientPos)
			
				-- замена текста
				SET @DestListText = STUFF(@DestListText, @RecipientPos,
					@TempPos - @RecipientPos, @Text)
			END
			-- нахождение следующего разделителя (',' или ';')
			SET @CommaPos = CHARINDEX(',', @strUpdString, @CommaPos + 1) + 1
			IF @CommaPos = 0
				SET @CommaPos = CHARINDEX(';', @strUpdString, @CommaPos + 1) + 1
		END
	
		-- обновление текста в таблице
		UPDATE dbo.GetSamples
		SET retstr = STUFF(@RetStr, @DestListPos,
			@MobileOperatorPos - @DestListPos,
			@DestListText)
		WHERE cardnum = @strCardNumber
			AND phonenum = @strPhoneNumber
	END

	RETURN 1
END
GO
/****** Object:  StoredProcedure [dbo].[mb_WWW_SendPassByCardNumber]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_WWW_SendPassByCardNumber]
    @requestId bigint,
    @card varchar(20)
AS
BEGIN
    DECLARE @pass AS varchar(20) = ABS(CHECKSUM(NewId())) % 100000;
    IF (@card='8888888888888888') BEGIN
        return 999;    
    END
    UPDATE
        dbo.GetClientByCardNumber
    SET Password = @pass
    WHERE 
        CardNumber = @card;
    IF (@@rowcount = 0) BEGIN
        RETURN -100;
    END;
    RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[mb_WWW_GetSamples]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[mb_WWW_GetSamples]
	@strCardNumber VARCHAR(20),
	@strPhoneNumber VARCHAR(20),
	@strRetStr VARCHAR(8000) OUTPUT
AS
BEGIN
	IF (@strCardNumber IS NULL)
		RAISERROR(50001, 16, 1)

	IF (0=dbo.IsDecimalString(@strCardNumber))
	BEGIN
		RAISERROR(50063, 16, 1, @strCardNumber)
		RETURN -5
	END

	IF (@strPhoneNumber IS NOT NULL)
	BEGIN
		IF (0=dbo.IsDecimalString(@strPhoneNumber))
		BEGIN
			RAISERROR(50063, 16, 1, @strPhoneNumber)
			RETURN -5
		END

		SELECT @strRetStr=retstr
		FROM dbo.GetSamples
		WHERE cardnum=@strCardNumber
		  AND phonenum=@strPhoneNumber

		RETURN 0
	END

	SELECT @strRetStr=retstr
	FROM GetSamples
	WHERE cardnum=@strCardNumber
	  AND phonenum IS NULL
	;

	RETURN 0
END
GO
/****** Object:  StoredProcedure [dbo].[mb_WWW_GetRegistrations2]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[mb_WWW_GetRegistrations2]
	@strCardNumber VARCHAR(20),
	@strRetStr VARCHAR(8000) OUTPUT
AS
BEGIN
	IF (@strCardNumber IS NULL)
		RAISERROR(50001, 16, 1)

	IF (0=dbo.IsDecimalString(@strCardNumber))
	BEGIN
		RAISERROR(50063, 16, 1, @strCardNumber)
		RETURN -5
	END
	
	SET @strRetStr = '';
	SELECT @strRetStr+=retstr+'#'
	FROM dbo.GetRegistrations2
	WHERE cardnum=@strCardNumber

	RETURN 0
END
GO
/****** Object:  StoredProcedure [dbo].[mb_WWW_GetRegistrations]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[mb_WWW_GetRegistrations]
	@strCardNumber VARCHAR(20),
	@strRetStr VARCHAR(8000) OUTPUT
AS
BEGIN
	IF (@strCardNumber IS NULL)
		RAISERROR(50001, 16, 1)

	IF (0=dbo.IsDecimalString(@strCardNumber))
	BEGIN
		RAISERROR(50063, 16, 1, @strCardNumber)
		RETURN -5
	END
	
	SET @strRetStr = '';
	SELECT @strRetStr+=retstr+'#'
	FROM dbo.GetRegistrations
	WHERE cardnum=@strCardNumber

	RETURN 0
END
GO
/****** Object:  StoredProcedure [dbo].[mb_WWW_GetPhoneQuickServices]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_WWW_GetPhoneQuickServices]
     @PhoneNumber VARCHAR(20)
AS
BEGIN
     SELECT PhoneNumber, QuickServices_Status
     FROM   Phone_QuickServices
     WHERE  PhoneNumber = @PhoneNumber
END
GO
/****** Object:  StoredProcedure [dbo].[mb_WWW_GetMobilePaymentCard]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_WWW_GetMobilePaymentCard]
	@phone varchar(20),
	@terbankCode tinyint,
	@branch smallint,
	@card varchar(20) OUT,
	@error INT OUT,
	@comment NVARCHAR(2000) OUT,
	@clientName nvarchar(500) OUT
AS
BEGIN
	DECLARE @RT as int
	
	select @card = card,
	       @error = error, 		
	       @comment = comment,
		   @clientName = clientName,
	       @RT = retCode
	from dbo.GetCardByPhone
	where phone = @phone and
          (@terbankCode IS NULL OR terBank = @terbankCode) and
	      (@branch IS NULL OR branch = @branch)
	RETURN @RT
END
GO
/****** Object:  StoredProcedure [dbo].[mb_WWW_GetMobileContactDate]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[mb_WWW_GetMobileContactDate]
                @ChangeDate DATE             
AS
BEGIN
    select
        phone.PhoneNumber as PhoneNumber, phone.ChangeType as ChangeType    
    from
        dbo.MBCast AS phone
    where
        phone.ChangeDate = @ChangeDate
 
    RETURN 0;
END;
GO
/****** Object:  StoredProcedure [dbo].[mb_WWW_GetMobileContact]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[mb_WWW_GetMobileContact]
    @PhoneNumbersToFind  VARCHAR(max),              --телефоны, проверяемые на подключение к МБ (разделитель ";")
    @PhoneNumbersFound  VARCHAR(max)  = NULL OUTPUT --телефоны, подключенные к МБ (разделитель ";")
AS
BEGIN
    DECLARE @phone AS VARCHAR(11), @id1 INT, @id2 INT;
    SET @PhoneNumbersFound = '';

    DECLARE c CURSOR READ_ONLY FAST_FORWARD FOR
    SELECT t.txt_value
    FROM dbo.fn_ParseText2Table(@PhoneNumbersToFind, ';') as t;

    -- Open the cursor
    OPEN c

    FETCH NEXT FROM c INTO @phone
    WHILE (@@FETCH_STATUS = 0)
    BEGIN
        -- проверяем номер телефона
        BEGIN TRY
            IF (@phone != '')
                EXEC dbo.mb_CheckPhoneNumber
                    @strPhoneNumber=@phone;
        END TRY
        BEGIN CATCH
            RETURN -1;
        END CATCH;

        -- ищем есть ли регистрация
		SET @id1 = NULL;
		SET @id2 = NULL;
        SELECT
            @id1 = reg.id,
            @id2 = reg2.id
        FROM
			dbo.GetRegistrations AS reg,
			dbo.GetRegistrations2 AS reg2
        WHERE
			reg.retstr LIKE '%'+@phone+'%'
			OR
			reg2.retstr LIKE '%'+@phone+'%';

        -- если регистрация есть, то записываем этот номер в output
		IF (@phone != '' AND (@id1 IS NOT NULL OR @id2 IS NOT NULL))
			SET @PhoneNumbersFound += @phone + ';';

        FETCH NEXT FROM c INTO @phone;
    END;

    -- Close and deallocate the cursor
    CLOSE c
    DEALLOCATE c

    RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[mb_ESB_SendSMS]    Script Date: 11/25/2014 05:00:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[mb_ESB_SendSMS]
	@iExternalSystemID int,
	@iMobileOperatorID tinyint,
	@strPhoneNumber varchar(20),
	@SMSID int,
	@vbMessageBody varbinary(500)
AS
BEGIN
	DECLARE @RC AS INT

	EXEC @RC = dbo.mb_CheckPhoneNumber
		@strPhoneNumber=@strPhoneNumber
	IF @RC<>0
		RETURN @RC
	
	BEGIN TRY
		BEGIN TRANSACTION;
		INSERT INTO smses
		VALUES (CURRENT_TIMESTAMP,
				@iExternalSystemID,
				@iMobileOperatorID,
				@SMSID,
				@strPhoneNumber,
				@vbMessageBody
		)
	    COMMIT TRANSACTION
		RETURN 0
	END TRY
	BEGIN CATCH
		ROLLBACK TRANSACTION
		SET @RC = ERROR_NUMBER()
	
		IF (@RC = 2601)
		BEGIN
			RAISERROR(50309, 16, 1, @iExternalSystemID, @SMSID)
			RETURN -4
		END
	
		IF (@RC = 547)
		BEGIN
			RAISERROR(50305, 16, 1, @iMobileOperatorID)
			RETURN -2
		END
	
		EXEC sp_raise_last_error
		RETURN -1
    END CATCH 
END;
GO
/****** Object:  Default [DF_card_smses_IgnoreBlockStatus]    Script Date: 11/25/2014 05:00:16 ******/
ALTER TABLE [dbo].[card_smses] ADD  CONSTRAINT [DF_card_smses_IgnoreBlockStatus]  DEFAULT ((0)) FOR [IgnoreBlockStatus]
GO
/****** Object:  Default [DF__ERMB_GetR__TryCo__6383C8BA]    Script Date: 11/25/2014 05:00:16 ******/
ALTER TABLE [dbo].[ERMB_GetRegistrations] ADD  DEFAULT ((1)) FOR [TryCount]
GO
/****** Object:  Default [DF__ermb_paym__TryCo__5AEE82B9]    Script Date: 11/25/2014 05:00:16 ******/
ALTER TABLE [dbo].[ermb_payment_card_request] ADD  DEFAULT ((100)) FOR [TryCount]
GO
/****** Object:  Default [DF__GetClient__Contr__20C1E124]    Script Date: 11/25/2014 05:00:16 ******/
ALTER TABLE [dbo].[GetClientByCardNumber] ADD  DEFAULT ((239)) FOR [ContrStatus]
GO
/****** Object:  Default [DF__GetClient__AddIn__21B6055D]    Script Date: 11/25/2014 05:00:16 ******/
ALTER TABLE [dbo].[GetClientByCardNumber] ADD  DEFAULT ((1)) FOR [AddInfoCn]
GO
/****** Object:  Default [DF__GetClient__Passw__24927208]    Script Date: 11/25/2014 05:00:16 ******/
ALTER TABLE [dbo].[GetClientByCardNumber] ADD  DEFAULT ('0') FOR [Password]
GO
/****** Object:  ForeignKey [FK_smses_mobile_operators]    Script Date: 11/25/2014 05:00:16 ******/
ALTER TABLE [dbo].[smses]  WITH CHECK ADD  CONSTRAINT [FK_smses_mobile_operators] FOREIGN KEY([mobileOperatorID])
REFERENCES [dbo].[mobile_operators] ([id])
GO
ALTER TABLE [dbo].[smses] CHECK CONSTRAINT [FK_smses_mobile_operators]
GO
CREATE TABLE MessagesFromMBK
(
    UESI_MessageID bigint not null,
    UESI_MessageTime DATETIME not null,
    UESI_MessageType VARCHAR(256) not null,
    UESI_MessageText VARCHAR(1024) not null
)
GO

CREATE PROCEDURE UESI_MessagesFromMBK

    @ExternalSystemID bigint

AS

BEGIN

    select * from MessagesFromMBK

END 
GO

CREATE PROCEDURE UESI_ConfirmMessages

    @ExternalSystemID bigint,
    @Messages varchar(1024)

AS

BEGIN

return

END 
GO


ALTER TABLE [dbo].[GetIMSIResult] ADD resultWithoutSend INT NOT NULL DEFAULT(1306060000)
GO

CREATE PROCEDURE [dbo].[mb_IMSI_CheckIMSI]
            @strPhoneNumber varchar(20),
            @iExternalSystemID int,
            @iMessageID bigint OUTPUT
AS
BEGIN
    DECLARE @messagId int;
    select @messagId = messId from dbo.GetIMSIResult where phone = @strPhoneNumber;
    IF @messagId IS NULL
        return 1305200001;
    ELSE
    BEGIN
        SET @iMessageID = @messagId;
        return 0;
    END;
END

CREATE PROCEDURE [dbo].[mb_IMSI_GetValidationResultEx]
            @iMessageID bigint,
            @iExternalSystemID int
AS
BEGIN
DECLARE @id int;
      select @id = id from dbo.GetIMSIResult where messId = @iMessageID;
      IF @id IS NULL
            return 1306068881;
      ELSE IF @iMessageID = 35
            return 1306060084;
      ELSE
            return 1306060000;

END

