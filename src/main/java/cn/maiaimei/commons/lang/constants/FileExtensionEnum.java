package cn.maiaimei.commons.lang.constants;

public enum FileExtensionEnum {

  TXT("txt"),
  CSV("csv"),
  JSON("json"),
  DOC("doc"),
  DOCX("docx"),
  XLS("xls"),
  XLSX("xlsx"),
  PDF("pdf"),
  ZIP("zip");

  private String extension;

  public String getExtension() {
    return extension;
  }

  FileExtensionEnum(String extension) {
    this.extension = extension;
  }

  public String getFileSuffix() {
    return String.format(".%s", this.extension);
  }
}
