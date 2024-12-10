import xml.etree.ElementTree as ET
import glob
import logging
import sys
import re
logger = logging.getLogger(__name__)
logger.setLevel(logging.INFO)
handler = logging.StreamHandler()
formatter = logging.Formatter('%(asctime)s : %(name)s  : %(funcName)s : %(levelname)s : %(message)s')
handler.setFormatter(formatter)
logger.addHandler(handler)


def main(arguments):
    base_path = arguments[1]
    xml_reports_path = arguments[2]
    logger.info(f"Base Path : {base_path}")
    logger.info("JUnit.xml File write start.......")
    all_xml_files = glob.glob(f'{base_path}/{xml_reports_path}/*.xml')
    logger.info(f"Parsing all Junit xml files for combined report.......{all_xml_files}")
    new_root = ET.Element("testsuites")
    for xml_file in all_xml_files:
        tree = ET.parse(xml_file)
        root = tree.getroot()
        new_root.append(root)
    with open(f'{base_path}/JUnit.xml', 'wb') as f:
        f.write(ET.tostring(new_root))
        logger.info("JUnit.xml File write success.......")

    # Workaround solution for issue below:
    # JUnitTestParser failed to handle JUnit.xml that contains numbers with comma separators
    # 'time="12,345,678.585"'
    pattern = r',(\d+)'
    replacement = r'\1'
    # Remove the comma separator from the numbers in JUnit.xml File
    with open(f'{base_path}/JUnit.xml', 'r') as f:
        old = f.read()
        new = re.sub(pattern, replacement, old)
        logger.info("Removing comma separator(s) for numbers in JUnit.xml File......")

    with open(f'{base_path}/JUnit.xml', 'w') as f:
        f.write(new)
        logger.info("Comma separator(s) for numbers in JUnit.xml File have been removed.")

if __name__ == '__main__':
    main(sys.argv)
