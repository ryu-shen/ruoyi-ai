import {AxiosProgressEvent} from 'axios';
import {http} from '../../utils/http/axios';

export function uploadApi(
  data: any,
  onUploadProgress?: (progressEvent: AxiosProgressEvent) => void
) {
  return http.request({
    url: `/agi/oss/upload`,
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    onUploadProgress,
  });
}
