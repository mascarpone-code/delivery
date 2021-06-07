package com.mascarpone.delivery.service.mail.templates;

import com.mascarpone.delivery.utils.Utils;

import java.util.Date;

public class FooterHtml {
    public static String getFooter(String MAIL_PSWD_COMMAMND, String MAIL_NAME_COMPANY) {
        return "                                                               </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                         </table>\n" +
                "                                                      </td>\n" +
                "                                                   </tr>\n" +
                "                                                </tbody>\n" +
                "                                             </table>\n" +
                "                                          </td>\n" +
                "                                       </tr>\n" +
                "                                       <tr style=\"border-collapse:collapse;\">\n" +
                "                                          <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-left:30px;padding-right:30px;padding-bottom:40px;\">\n" +
                "                                             <!--[if mso]>\n" +
                "                                             <table width=\"540\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                                                <tr>\n" +
                "                                                   <td width=\"260\" valign=\"top\">\n" +
                "                                                      <![endif]-->\n" +
                "                                                      <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;\">\n" +
                "                                                         <tbody>\n" +
                "                                                            <tr style=\"border-collapse:collapse;\">\n" +
                "                                                               <td class=\"es-m-p20b\" width=\"260\" align=\"left\" style=\"padding:0;Margin:0;\">\n" +
                "                                                                  <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                "                                                                     <tbody>\n" +
                "                                                                        <tr style=\"border-collapse:collapse;\">\n" +
                "                                                                           <td align=\"left\" style=\"padding:0;Margin:0;\">\n" +
                "                                                                              <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:tahoma, verdana, segoe, sans-serif;line-height:21px;color:#666666;\"><span style=\"font-weight:bold;line-height:150%;\">" + MAIL_PSWD_COMMAMND + " " + MAIL_NAME_COMPANY + "</span></p>\n" +
                "                                                                           </td>\n" +
                "                                                                        </tr>\n" +
                "                                                                     </tbody>\n" +
                "                                                                  </table>\n" +
                "                                                               </td>\n" +
                "                                                            </tr>\n" +
                "                                                         </tbody>\n" +
                "                                                      </table>\n" +
                "                                                      <!--[if mso]>\n" +
                "                                                   </td>\n" +
                "                                                   <td width=\"20\"></td>\n" +
                "                                                   <td width=\"260\" valign=\"top\">\n" +
                "                                                      <![endif]-->\n" +
                "                                                      <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right;\">\n" +
                "                                                         <tbody>\n" +
                "                                                            <tr style=\"border-collapse:collapse;\">\n" +
                "                                                               <td width=\"260\" align=\"left\" style=\"padding:0;Margin:0;\">\n" +
                "                                                                  <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                "                                                                     <tbody>\n" +
                "                                                                        <tr style=\"border-collapse:collapse;\">\n" +
                "                                                                           <td align=\"right\" style=\"padding:0;Margin:0;\">\n" +
                "                                                                              <p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:tahoma, verdana, segoe, sans-serif;line-height:21px;color:#666666;\">" + Utils.formatDate(new Date()) + "</p>\n" +
                "                                                                           </td>\n" +
                "                                                                        </tr>\n" +
                "                                                                     </tbody>\n" +
                "                                                                  </table>\n" +
                "                                                               </td>\n" +
                "                                                            </tr>\n" +
                "                                                         </tbody>\n" +
                "                                                      </table>\n" +
                "                                                      <!--[if mso]>\n" +
                "                                                   </td>\n" +
                "                                                </tr>\n" +
                "                                             </table>\n" +
                "                                             <![endif]-->\n" +
                "                                          </td>\n" +
                "                                       </tr>\n" +
                "                                    </tbody>\n" +
                "                                 </table>\n" +
                "                              </td>\n" +
                "                           </tr>\n" +
                "                        </tbody>\n" +
                "                     </table>\n" +
                "                     <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;\">\n" +
                "                        <tbody>\n" +
                "                           <tr style=\"border-collapse:collapse;\"></tr>\n" +
                "                           <tr style=\"border-collapse:collapse;\">\n" +
                "                              <td align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                "                                 <table class=\"es-footer-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;\">\n" +
                "                                    <tbody>\n" +
                "                                       <tr style=\"border-collapse:collapse;\">\n" +
                "                                          <td align=\"left\" style=\"padding:0;Margin:0;\">\n" +
                "                                             <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                "                                                <tbody>\n" +
                "                                                   <tr style=\"border-collapse:collapse;\">\n" +
                "                                                      <td width=\"600\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                "                                                         <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                "                                                            <tbody>\n" +
                "                                                               <tr style=\"border-collapse:collapse;\">\n" +
                "                                                                  <td align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                "                                                                     <table width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                "                                                                        <tbody>\n" +
                "                                                                           <tr style=\"border-collapse:collapse;\">\n" +
                "                                                                              <td style=\"padding:0;Margin:0px;border-bottom:4px solid #CCCCCC;background:rgba(0, 0, 0, 0) none repeat scroll 0% 0%;height:1px;width:100%;margin:0px;\"></td>\n" +
                "                                                                           </tr>\n" +
                "                                                                        </tbody>\n" +
                "                                                                     </table>\n" +
                "                                                                  </td>\n" +
                "                                                               </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                         </table>\n" +
                "                                                      </td>\n" +
                "                                                   </tr>\n" +
                "                                                </tbody>\n" +
                "                                             </table>\n" +
                "                                          </td>\n" +
                "                                       </tr>\n" +
                "                                       <tr style=\"border-collapse:collapse;\">\n" +
                "                                          <td align=\"left\" style=\"padding:0;Margin:0;padding-top:30px;padding-left:30px;padding-right:30px;\">\n" +
                "                                             <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                "                                                <tbody>\n" +
                "                                                   <tr style=\"border-collapse:collapse;\">\n" +
                "                                                      <td width=\"540\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;\">\n" +
                "                                                         <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;\">\n" +
                "                                                            <tbody>\n" +
                "                                                               <tr style=\"border-collapse:collapse;\">\n" +
                "                                                                  <td align=\"center\" style=\"padding:0;Margin:0;display:none;\"></td>\n" +
                "                                                               </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                         </table>\n" +
                "                                                      </td>\n" +
                "                                                   </tr>\n" +
                "                                                </tbody>\n" +
                "                                             </table>\n" +
                "                                          </td>\n" +
                "                                       </tr>\n" +
                "                                    </tbody>\n" +
                "                                 </table>\n" +
                "                              </td>\n" +
                "                           </tr>\n" +
                "                        </tbody>\n" +
                "                     </table>\n" +
                "                  </td>\n" +
                "               </tr>\n" +
                "            </tbody>\n" +
                "         </table>\n" +
                "      </div>\n" +
                "   </body>\n" +
                "</html>";
    }
}
