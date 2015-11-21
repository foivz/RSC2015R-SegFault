<?php
namespace backend\modules\posts\models;

use yii\base\Model;
use yii\web\UploadedFile;

class File extends Model
{
    /**
     * @var UploadedFile
     */
    public $image;

    public function rules()
    {
        return [
            [['image'], 'file', 'skipOnEmpty' => false, 'extensions' => 'png, jpg'],
        ];
    }

    public function upload($name = null)
    {
        if ($this->validate()) {
            if(!$name) $name = $this->image->baseName;
            $this->image->saveAs('uploads/' . $name . '.' . $this->image->extension);
            return true;
        } else {
            return false;
        }
    }

    public function getExtension()
    {
        if ($this->validate()) {
            return $this->image->extension;
        } else {
            return null;
        }
    }
}
?>