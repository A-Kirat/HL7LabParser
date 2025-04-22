# HL7LabParser

A lightweight Java desktop application for parsing and displaying basic lab test information from HL7 messages using a Swing-based GUI.

---

## 💡 Features

- Parses standard HL7 v2.x lab messages (`ORU^R01`)
- Extracts and displays:
  - Sample ID
  - Test Name (from OBX segment)
  - Test Result Value and Unit
  - Reference Range
  - Interpretation Flag (Normal, High, Low)
- Simple and modern Swing UI with hover effects and clean layout
- Ready for testing or teaching HL7 structure and segment logic

---

## 🧪 HL7 Example Input

```hl7
MSH|^~\&|DEVICE|LAB|LIS|HOSPITAL|20250421||ORU^R01|123|P|2.5
PID|1||123456^^^HOSPITAL||Ahmed^Kirat
OBR|1||ORD123||GLUCOSE^Glucose Test
OBX|1|NM|2345-7^Glucose^LN||5.6|mmol/L|4.0-6.0|N
