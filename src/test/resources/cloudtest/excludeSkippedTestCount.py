import xml.etree.ElementTree as ET
import logging
import sys

logger = logging.getLogger(__name__)
logger.setLevel(logging.INFO)
handler = logging.StreamHandler()
formatter = logging.Formatter('%(asctime)s : %(name)s  : %(funcName)s : %(levelname)s : %(message)s')
handler.setFormatter(formatter)
logger.addHandler(handler)


def main(arguments):
    xml_report = arguments[1]
    logger.info(f"SureFire XML test report: {xml_report}")
    # file= "C:\\dev\\conversation-conductor-hm-e2e\\target\surefire-reports\\TEST-com.msft.onecall.client.RunCucumberTest.xml"
    logger.info(f"Removing entries for skipped test cases from TEST-com.msft.onecall.client.RunCucumberTest.xml...")
    tree = ET.parse(xml_report)
    root = tree.getroot()
    index = 0
    for testcase in root.findall('testcase'):
        index = index + 1
        logger.info(f"{index}: {testcase.get('name')}")
        if len(testcase.findall('skipped')):
            skipped_message = testcase.find('skipped').get('message')
            logger.info(f"skipped_message: {skipped_message}")
            if skipped_message:
                # remove this skipped testcase from XML
                root.remove(testcase)
        else:
            logger.info("This is an executed testcase.")

    # Update the xml file
    tree.write(xml_report)
    logger.info("RunCucumberTest.xml has been updated successfully.")


if __name__ == '__main__':
    main(sys.argv)
